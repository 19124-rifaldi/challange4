package com.binar.challange42.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challange42.NoteAdapter
import com.binar.challange42.R
import com.binar.challange42.databinding.FragmentHomeBinding
import com.binar.challange42.dialog.AddFragment
import com.binar.challange42.room.NoteData
import com.binar.challange42.room.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.zip.Inflater


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var login: SharedPreferences
    private var database : NoteDatabase? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        database = NoteDatabase.getInstance(requireContext())
        login=requireContext().getSharedPreferences("datauser",Context.MODE_PRIVATE)
        val getdata = login.getString("username","")
        binding.welcomeTextView.text = "welcome,${getdata}"
        getData()

        binding.recycler.layoutManager=
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)



        binding.addFab.setOnClickListener{
            val alertDialog = AddFragment()
            alertDialog.show(parentFragmentManager,"fragmentdialog")

        }

        login=requireContext().getSharedPreferences("datauser",Context.MODE_PRIVATE)
        binding.logoutTv.setOnClickListener{
            logout()
        }
        getData()

    }



    override fun onResume() {
        super.onResume()
        getData()
    }
    fun who(){
        login=requireContext().getSharedPreferences("datauser",Context.MODE_PRIVATE)
        val getdata = login.getString("username","")
        binding.welcomeTextView.text = "welcome,${getdata}"
    }

    fun logout(){
        val keluar = AlertDialog.Builder(requireContext())
        keluar.setTitle("logut")
        keluar.setMessage("Ingin log out?")
        keluar.setCancelable(false)
        val keluar1 = keluar.create()
        keluar.setPositiveButton("ya"){ dialogInterface: DialogInterface, i: Int ->
            val logout = login.edit()
            logout.clear()
            logout.apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
        }.setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
            keluar1.dismiss()
            Toast.makeText(requireContext(), "Batal Logout", Toast.LENGTH_LONG).show()
        }
        keluar.show()
    }

    fun getData(){
        recycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        GlobalScope.launch {
            val listD = database?.noteDbo()?.getAllNoteTaking()

            activity?.runOnUiThread {
                listD.let {
                    val adp = NoteAdapter(it!!)
                    recycler.adapter = adp
                }
            }
        }

    }

}