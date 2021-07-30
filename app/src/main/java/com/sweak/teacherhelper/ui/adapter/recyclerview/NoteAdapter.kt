package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.database.entity.Note
import com.sweak.teacherhelper.databinding.NoteItemBinding

class NoteAdapter(
    private var optionsMenuClickListener: OptionsMenuClickListener
) : ListAdapter<Note, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {

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
            with (getItem(holder.absoluteAdapterPosition)) {
                binding.textViewNoteTitle.text = this.title
                binding.textViewNoteDescription.text = this.description

                binding.textViewStudentActivityOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    fun getNoteAt(position: Int): Note = getItem(position)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return (oldItem.title == newItem.title) and
                            (oldItem.description == newItem.description)
                }
            }
    }
}