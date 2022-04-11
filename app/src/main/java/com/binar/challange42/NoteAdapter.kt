package com.binar.challange42

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.challange42.dialog.AddFragment
import com.binar.challange42.dialog.EditFragment
import com.binar.challange42.room.NoteData
import com.binar.challange42.room.NoteDatabase
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class NoteAdapter(val dataNote : List<NoteData>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var database : NoteDatabase? =null
        class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.judul_tv.text =dataNote[position].judul
        holder.itemView.isi_tv.text  =dataNote[position].catatan

        holder.itemView.delete_button.setOnClickListener{
            database = NoteDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Yakin Menghapus Data ?")

                .setPositiveButton("Ya" ) { dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.async {
                        val result = database?.noteDbo()?.deleteNoteTaking(dataNote[position])

                        (holder.itemView.context as MainActivity).runOnUiThread {
                            if (result != 0){
                                (holder.itemView.context as MainActivity).recreate()
                                (holder.itemView.context as MainActivity).overridePendingTransition(0,0)
                                Toast.makeText(it.context, "Data ${dataNote[position].id} Berhasil Dihapus", Toast.LENGTH_LONG).show()
                                dialogInterface.dismiss()
                            } else{
                                Toast.makeText(it.context, "Data ${dataNote[position].id} Gagal Dihapus", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                } .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(it.context, "Data ${dataNote[position].id} Tidak Jadi Dihapus", Toast.LENGTH_LONG).show()
                    dialogInterface.dismiss()
                }
                .show()

        }
        holder.itemView.edit_button.setOnClickListener{
                database= NoteDatabase.getInstance(it.context)

                val edit = LayoutInflater.from(it.context).inflate(R.layout.fragment_edit,null,false)
                val editShow = AlertDialog.Builder(it.context).setView(edit).create()
                edit.editjudul_et.setText(dataNote[position].judul)
                edit.editisi_et.setText(dataNote[position].catatan)
                edit.edit_but.setOnClickListener{
                    val jdl = edit.editjudul_et.text.toString()
                    val isi = edit.editisi_et.text.toString()

                    dataNote[position].judul=jdl
                    dataNote[position].catatan= isi

                    GlobalScope.async {
                        val dB = database?.noteDbo()?.updateNoteTaking(dataNote[position])
                        (edit.context as MainActivity).runOnUiThread{
                            if (dB != 0){
                                (holder.itemView.context as MainActivity).recreate()
                                Toast.makeText(it.context, "Data ${dataNote[position].judul} berhasil diedit", Toast.LENGTH_LONG).show()
                                editShow.dismiss()
                            }else{
                                Toast.makeText(it.context, "Data ${dataNote[position].judul} gagal di edit", Toast.LENGTH_LONG).show()
                                editShow.dismiss()
                            }

                        }

                    }

                }
                editShow.show()
//            holder.itemView.judul_tv.text =dataNote[position].judul
//            holder.itemView.isi_tv.text  =dataNote[position].catatan
//            val mDialogFormFragment = EditFragment()
//            val bundle = Bundle()
//            val fragmentManager = (it.context as AppCompatActivity).supportFragmentManager.beginTransaction()
//            mDialogFormFragment.show(fragmentManager,null)





        }
    }

    override fun getItemCount(): Int {
        return dataNote.size
    }


}