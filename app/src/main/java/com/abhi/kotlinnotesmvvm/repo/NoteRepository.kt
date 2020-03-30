package com.abhi.kotlinnotesmvvm.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.abhi.kotlinnotesmvvm.dao.NoteDao
import com.abhi.kotlinnotesmvvm.database.Note
import com.abhi.kotlinnotesmvvm.database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(var context: Application) {

    private var noteDao: NoteDao

    private var allNotes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(context)
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.insert(note)
    }


    fun update(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.update(note)
    }


    fun delete(note: Note) = CoroutineScope(Dispatchers.IO).launch {
        noteDao.delete(note)
    }


    fun deleteAll() = CoroutineScope(Dispatchers.IO).launch {
        noteDao.deleteAllNotes()
    }

    fun getAllNotes() = allNotes

}