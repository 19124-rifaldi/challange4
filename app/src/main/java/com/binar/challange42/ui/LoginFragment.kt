package com.binar.challange42.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.binar.challange42.R
import com.binar.challange42.databinding.FragmentLoginBinding
import com.binar.challange42.room.user.UserDatabase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var dataBase : UserDatabase? = null
    lateinit var save : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentLoginBinding.bind(view)
        if (requireContext().getSharedPreferences("datauser", Context.MODE_PRIVATE).contains("username")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
        }else{
            lgn()
        }
        toRegis()
        lgn()

    }

    private fun toRegis(){
        binding.registerTv.setOnClickListener{
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun lgn(){
        binding.loginBt.setOnClickListener{
            dataBase = UserDatabase.getInstance(requireContext())

            val email = binding.emailEd.text.toString()
            val pass = binding.passwordEd.text.toString()

            val login = dataBase?.userDao()?.getPengguna(email,pass)
            if (email =="" || pass ==""){
                Toast.makeText(requireContext(), "lengkapi data", Toast.LENGTH_LONG).show()
            }else if (login.isNullOrEmpty()){
                Toast.makeText(requireContext(), "email dan password blm terdaftar", Toast.LENGTH_LONG).show()
            }else{
                val save = requireContext().getSharedPreferences("datauser",Context.MODE_PRIVATE)
                val savep = save.edit()
                savep.putString("username",login)
                savep.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)

            }
        }
    }

}