package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.Student
import com.sweak.teacherhelper.databinding.StudentItemBinding

class StudentAdapter(
    private val context: Context,
    private var itemClickListener: ItemClickListener,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentHolder>()  {

    private var students: List<Student> = ArrayList()

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
            with (students[position]) {
                binding.textViewStudentFullName.text =
                    context.getString(R.string.full_name_template, this.firstName, this.lastName)
                binding.textViewStudentDetails.text = this.className

                binding.textViewStudentOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }

                binding.root.setOnClickListener {
                    itemClickListener.onItemClickListener(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = students.size

    fun setStudents(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): Student = students[position]
}