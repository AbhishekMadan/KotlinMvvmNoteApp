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
        noteViewModel
            .insertNote(Note(et_title.text.toString(), et_desc.text.toString(), priority_pk.value))
        finish()
    }
}
