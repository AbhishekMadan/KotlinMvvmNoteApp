package com.abhi.kotlinnotesmvvm.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abhi.kotlinnotesmvvm.database.Note

// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}