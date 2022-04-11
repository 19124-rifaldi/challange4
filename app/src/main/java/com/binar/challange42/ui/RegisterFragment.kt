package com.binar.challange42.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.binar.challange42.R
import com.binar.challange42.databinding.FragmentRegisterBinding
import com.binar.challange42.room.user.User
import com.binar.challange42.room.user.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var dataBase : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        daftar()

    }
    private fun daftar(){
        binding.regBt.setOnClickListener{
            if (binding.usernameEd.text.toString()==""||
                binding.emailEd.text.toString()==""||
                binding.passwordEd.text.toString()=="" ||
                binding.confpassEd.text.toString()=="") {
                Toast.makeText(requireContext(), "lengkapi data dulu!!!", Toast.LENGTH_LONG).show()

            }else if (binding.passwordEd.text.toString()!= binding.confpassEd.text.toString()){
                Toast.makeText(requireContext(), "Password dan konfirmasi password harus sesuai", Toast.LENGTH_LONG).show()

            }else{
                proses()
                Toast.makeText(requireContext(), "regis berhasil", Toast.LENGTH_LONG).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)

            }
        }
    }
    private fun proses(){
        dataBase = UserDatabase.getInstance(requireContext())
        GlobalScope.async {
            val username = binding.usernameEd.text.toString()
            val email = binding.emailEd.text.toString()
            val password = binding.passwordEd.text.toString()
            val konfirm_password = binding.confpassEd.text.toString()

            val regist =dataBase?.userDao()?.insertUser(User(null,email,username,password))
            requireActivity().runOnUiThread {
                if (regist != 0.toLong()){
                    Toast.makeText(requireContext(), "Pendaftaran telah berhasil", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(requireContext(), "Pendaftaran gagal", Toast.LENGTH_LONG).show()

                }
            }
        }
    }

}