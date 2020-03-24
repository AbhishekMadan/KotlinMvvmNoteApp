package com.abhi.kotlinnotesmvvm.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abhi.kotlinnotesmvvm.dao.NoteDao

// https://medium.com/mindorks/room-kotlin-android-architecture-components-71cad5a1bb35

@Database(entities = [Note::class],
    version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private val TAG = "[NoteDatabase]"
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
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.d(TAG, "Db onCreate finish.")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Log.d(TAG, "Db onOpen.")
                }
            })
            .build()
    }
}