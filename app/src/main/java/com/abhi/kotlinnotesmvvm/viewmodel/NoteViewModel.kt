package com.abhi.kotlinnotesmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.abhi.kotlinnotesmvvm.database.Note
import com.abhi.kotlinnotesmvvm.repo.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)
    private lateinit var noteList: LiveData<List<Note>>

    init {
        noteList = repository.getAllNotes()
    }

    fun insertNote(note: Note) = repository.insert(note)

    fun deleteNote(note: Note) = repository.delete(note)

    fun updateNote(note: Note) = repository.update(note)

    fun deleteAllNotes() = repository.deleteAll()

    fun getAllNotes() = noteList
}