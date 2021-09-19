package com.sweak.teacherhelper.features.studentcontrol

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.data.database.entity.MissingKitTuple
import com.sweak.teacherhelper.data.database.entity.Student
import com.sweak.teacherhelper.databinding.StudentControlSessionItemBinding

class StudentControlAdapter(
    private var context: Context,
    private var checkboxCheckedListener: CheckboxCheckedListener,
    private var spinnerItemSelectedListener: SpinnerItemSelectedListener
    ) : RecyclerView.Adapter<StudentControlAdapter.StudentControlHolder>() {

    private var students: List<Student> = ArrayList()
    private var missingKits: List<MissingKitTuple> = ArrayList()
    private lateinit var activityAdapter: ArrayAdapter<CharSequence>

    class StudentControlHolder(val binding: StudentControlSessionItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    interface CheckboxCheckedListener {
        fun onChecked(isChecked: Boolean, position: Int)
    }

    interface SpinnerItemSelectedListener {
        fun onSelected(parent: AdapterView<*>?, adapterViewPosition: Int, recyclerViewPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentControlHolder {
        val binding = StudentControlSessionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentControlHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentControlHolder, position: Int) {
        with (holder) {
            with(students[holder.absoluteAdapterPosition]) {
                binding.textViewStudentFullName.text =
                    context.getString(R.string.full_name_template, this.firstName, this.lastName)
                binding.textViewMissingKitCount.text =
                    context.getString(R.string.missing_kit_count, getMissingKitCountOfStudent(this.id))
                prepareActivitySpinner(binding)

                binding.checkboxMissingKit.setOnCheckedChangeListener {_, isChecked ->
                    checkboxCheckedListener.onChecked(isChecked, holder.absoluteAdapterPosition)
                }

                binding.spinnerActivity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, adapterViewPosition: Int, id: Long)
                    {
                        spinnerItemSelectedListener.onSelected(
                            parent, adapterViewPosition, holder.absoluteAdapterPosition)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun getMissingKitCountOfStudent(id: Int): Int
    {
        for (missingKitCount in missingKits) {
            if (missingKitCount.studentId == id)
                return missingKitCount.missingKitCount
        }
        return 0
    }

    private fun prepareActivitySpinner(binding: StudentControlSessionItemBinding) {
        activityAdapter = ArrayAdapter.createFromResource(context,
            R.array.activity_grades, android.R.layout.simple_spinner_item)

        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerActivity.adapter = activityAdapter
        binding.spinnerActivity.setSelection(0)
    }

    override fun getItemCount(): Int = students.size

    @SuppressLint("NotifyDataSetChanged")
    fun setStudents(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): Student = students[position]

    @SuppressLint("NotifyDataSetChanged")
    fun setMissingKits(missingKits: List<MissingKitTuple>) {
        this.missingKits = missingKits
        notifyDataSetChanged()
    }
}