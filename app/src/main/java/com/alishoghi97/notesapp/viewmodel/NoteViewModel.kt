package com.alishoghi97.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alishoghi97.notesapp.models.NoteEntity
import com.alishoghi97.notesapp.repository.NoteRepository

public class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)
    private var allNoteList = repository.getAllNotes()

    suspend fun insert(note : NoteEntity) {
        repository.insertNote(note)
    }

    suspend fun update(note : NoteEntity) {
        if (note.id <= 0) return
        repository.updateNote(note)
    }

    suspend fun delete(note : NoteEntity) {
        if (note.id <= 0) return
        repository.deleteNote(note)
    }

    fun getAllData(): LiveData<List<NoteEntity>> {
        return allNoteList
    }

    fun searchNotes(query: String): LiveData<List<NoteEntity>> {
        return repository.searchNotes(query)
    }
}