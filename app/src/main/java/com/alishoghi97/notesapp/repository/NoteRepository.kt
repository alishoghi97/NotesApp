package com.alishoghi97.notesapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.alishoghi97.notesapp.dao.NoteDao
import com.alishoghi97.notesapp.db.NoteDatabase
import com.alishoghi97.notesapp.models.NoteEntity

class NoteRepository(application: Application) {

    private var noteDao : NoteDao
    private lateinit var allNoteList: LiveData<List<NoteEntity>>

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao= database.noteDao()
        allNoteList = noteDao.getAllNotes()
    }

    suspend fun insertNote(note:NoteEntity) {
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<NoteEntity>> {
        return allNoteList
    }

    fun searchNotes(query: String): LiveData<List<NoteEntity>> {
        return noteDao.searchNotes("%$query%")
    }
}