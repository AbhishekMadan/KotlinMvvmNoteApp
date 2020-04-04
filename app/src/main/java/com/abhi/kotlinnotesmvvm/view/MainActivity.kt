package com.abhi.kotlinnotesmvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhi.kotlinnotesmvvm.R
import com.abhi.kotlinnotesmvvm.view.adapter.NoteAdapter
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModeFactory
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initViewModel()
    }

    private fun initView() {
        rv_note_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            noteAdapter = NoteAdapter()
            adapter = noteAdapter
        }
        bt_add_note.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNoteActivity::class.java))
        }
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.adapterPosition))
            }
        }
        ItemTouchHelper(itemTouchCallback)
            .attachToRecyclerView(rv_note_list)
    }

    private fun initViewModel() {
        noteViewModel = ViewModelProvider(this,
            NoteViewModeFactory(application))
            .get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()
            .observe(this, Observer {
                // Add list to recycler view.
                noteAdapter.refreshNotes(it)
                noteAdapter.notifyDataSetChanged()
            })
    }
}
