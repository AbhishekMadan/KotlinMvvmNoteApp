package com.abhi.kotlinnotesmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abhi.kotlinnotesmvvm.R
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModeFactory
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    private fun initViewModel() {
        noteViewModel = ViewModelProvider(this,
            NoteViewModeFactory(application))
            .get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()
            .observe(this, Observer {
                // Add list to recycler view.
            })
    }
}
