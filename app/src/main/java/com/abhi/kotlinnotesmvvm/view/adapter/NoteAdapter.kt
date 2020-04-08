package com.abhi.kotlinnotesmvvm.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhi.kotlinnotesmvvm.R
import com.abhi.kotlinnotesmvvm.database.Note
import kotlinx.android.synthetic.main.note_element.view.*

class NoteAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.note_element, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> holder.bind(notes[position])
        }
    }

    override fun getItemCount(): Int = notes.size

    fun refreshNotes(noteList: List<Note>) {
        notes.clear()
        notes.addAll(noteList)
    }

    fun getNoteAt(position: Int) = notes[position]

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_priority.text = note.priority.toString()
            tv_title.text = note.title
            tv_desc.text = note.description
        }
    }
}