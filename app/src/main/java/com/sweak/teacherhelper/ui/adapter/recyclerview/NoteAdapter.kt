package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.database.entity.Note
import com.sweak.teacherhelper.databinding.NoteItemBinding

class NoteAdapter(
    private var optionsMenuClickListener: OptionsMenuClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = ArrayList()

    inner class NoteHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = NoteItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        with (holder) {
            with (notes[position]) {
                binding.textViewNoteTitle.text = this.title
                binding.textViewNoteDescription.text = this.description

                binding.textViewStudentActivityOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = notes[position]
}