package com.binar.challange42.room

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteData")
    fun getAllNoteTaking() : List<NoteData>

    @Insert
    fun insertNoteTaking(noteData: NoteData) : Long

    @Update
    fun updateNoteTaking(noteData: NoteData) : Int

    @Delete
    fun deleteNoteTaking(noteData: NoteData) : Int
}