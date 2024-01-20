package com.alishoghi97.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alishoghi97.notesapp.models.NoteEntity

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM noteentity order by id asc")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM noteentity WHERE title LIKE :query OR content LIKE :query")
    fun searchNotes(query: String): LiveData<List<NoteEntity>>
}