package com.abhi.kotlinnotesmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://medium.com/mindorks/room-kotlin-android-architecture-components-71cad5a1bb35

@Database(entities = [Note::class],
    version = 1)
abstract class NoteDatabase : RoomDatabase() {

    companion object {

        private val DATABASE_NAME = "note_db"
        private val LOCK = Any()
        @Volatile var instance: NoteDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(LOCK) {
                createDatabase(context)
                    .also {
                        instance = it
                    }
            }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}