package com.binar.challange42.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteData::class], version = 1)

abstract class NoteDatabase :RoomDatabase() {
    abstract fun noteDbo(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "NoteTaking.db"
                    ).build()
                }

            }
            return INSTANCE
        }
    }
    fun destroyInstance(){
        INSTANCE = null
    }
}
