package com.abhi.kotlinnotesmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.abhi.kotlinnotesmvvm.R
import com.abhi.kotlinnotesmvvm.database.Note
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModeFactory
import com.abhi.kotlinnotesmvvm.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    companion object {
        const val EXTRA_ID = "com.abhi.extra_note_id"
        const val MIN_VAL = 0
        const val MAX_VAL = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        initView()
        initViewModel()

        if(intent?.hasExtra(EXTRA_ID)!!) {
            intent.getIntExtra(EXTRA_ID, -1).let {
                CoroutineScope(Dispatchers.Main).launch {
                    val note = async(Dispatchers.IO) { noteViewModel.getNote(it) }
                    // Log.i("MC-999", "Note: ${note.await().toString()}")
                    note.await()?.let {
                        et_title.setText(it.title)
                        et_desc.setText(it.description)
                        priority_pk.value = it.priority
                    }
                }
            }
        }
    }

    private fun initView() {
        priority_pk.minValue = MIN_VAL
        priority_pk.maxValue = MAX_VAL

        setTitle("Add Note")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    private fun initViewModel() {
        noteViewModel = ViewModelProvider(this@AddNoteActivity,
            NoteViewModeFactory(application))
            .get(NoteViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        if (et_title.text.toString().isEmpty() || et_desc.text.toString().isEmpty()) {
            Toast.makeText(this@AddNoteActivity, "Fields are empty!", Toast.LENGTH_SHORT)
                .show()
            return;
        }
        val note = Note(et_title.text.toString(), et_desc.text.toString(), priority_pk.value)
        val noteId = intent.getIntExtra(EXTRA_ID, -1)
        if (noteId > -1) {
            note.id = noteId
            noteViewModel.updateNote(note)
        } else {
            noteViewModel.insertNote(note)
        }
        finish()
    }
}
