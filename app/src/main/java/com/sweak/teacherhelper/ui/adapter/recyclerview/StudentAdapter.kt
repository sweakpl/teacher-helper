package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.Student
import com.sweak.teacherhelper.databinding.StudentItemBinding

class StudentAdapter(
    private val context: Context,
    private var itemClickListener: ItemClickListener,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : ListAdapter<Student, StudentAdapter.StudentHolder>(DIFF_CALLBACK)  {

    class StudentHolder(val binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
    }

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val binding = StudentItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        with (holder) {
            with (getItem(holder.absoluteAdapterPosition)) {
                binding.textViewStudentFullName.text =
                    context.getString(R.string.full_name_template, this.firstName, this.lastName)
                binding.textViewStudentDetails.text = this.className

                binding.textViewStudentOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(holder.absoluteAdapterPosition)
                }

                binding.root.setOnClickListener {
                    itemClickListener.onItemClickListener(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    fun getStudentAt(position: Int): Student = getItem(position)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Student> =
            object : DiffUtil.ItemCallback<Student>() {
                override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return (oldItem.firstName == newItem.firstName) and
                            (oldItem.lastName == newItem.lastName) and
                            (oldItem.className == newItem.className) and
                            (oldItem.groupId == newItem.groupId)
                }
            }
    }
}