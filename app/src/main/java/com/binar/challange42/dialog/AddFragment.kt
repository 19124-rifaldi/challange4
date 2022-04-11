package com.binar.challange42.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.binar.challange42.R
import com.binar.challange42.databinding.FragmentAddBinding
import com.binar.challange42.room.NoteData
import com.binar.challange42.room.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class AddFragment : DialogFragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var database: NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)

        database = NoteDatabase.getInstance(requireContext())

        binding.addBut.setOnClickListener {
            val objectNote = NoteData(
                null,
                binding.judulEt.text.toString(),
                binding.isiEt.text.toString()
            )
            GlobalScope.async {
                val result = database?.noteDbo()?.insertNoteTaking(objectNote)
                activity?.runOnUiThread {
                    if (result != 0.toLong()) {
                        Toast.makeText(requireContext(), "Sukses menambahkan ", Toast.LENGTH_LONG)
                            .show()
                        dialog?.dismiss()
                    } else {
                        Toast.makeText(requireContext(), "gagal menambahkan ", Toast.LENGTH_LONG)
                            .show()
                    }

                }

            }
            dialog?.dismiss()
        }

    }

    override fun onDetach() {
        super.onDetach()
        activity?.recreate()
    }
}



