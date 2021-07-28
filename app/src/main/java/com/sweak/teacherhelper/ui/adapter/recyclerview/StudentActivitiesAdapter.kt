package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.StudentActivity
import com.sweak.teacherhelper.databinding.StudentActivityItemBinding

class StudentActivitiesAdapter(
    private val context: Context,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : RecyclerView.Adapter<StudentActivitiesAdapter.StudentActivityHolder>() {

    private var studentActivities: List<StudentActivity> = ArrayList()

    class StudentActivityHolder(val binding: StudentActivityItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentActivityHolder {
        val binding = StudentActivityItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentActivityHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentActivityHolder, position: Int) {
        with (holder) {
            with (studentActivities[position]) {
                binding.textViewStudentActivityType.text = when (this.activityType) {
                    StudentActivity.MISSING_KIT_ACTIVITY_TYPE ->
                        context.getString(R.string.missing_kit)
                    else ->
                        context.getString(R.string.activity) + ": " + this.activityType
                }

                when (this.activityType) {
                    StudentActivity.MISSING_KIT_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.YELLOW)
                    StudentActivity.PLUS_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.GREEN)
                    StudentActivity.MINUS_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.RED)
                }

                binding.textViewStudentActivityOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = studentActivities.size

    fun setStudentActivities(studentActivities: List<StudentActivity>) {
        this.studentActivities = studentActivities
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): StudentActivity = studentActivities[position]
}