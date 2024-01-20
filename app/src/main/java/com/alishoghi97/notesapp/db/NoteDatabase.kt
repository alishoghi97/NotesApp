package com.alishoghi97.notesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alishoghi97.notesapp.dao.NoteDao
import com.alishoghi97.notesapp.models.NoteEntity
import com.alishoghi97.notesapp.utils.Converters

@Database(entities = [NoteEntity::class], version = 4)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java, "note.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance as NoteDatabase
        }
    }
}