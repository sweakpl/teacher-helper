package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.StudentActivity
import com.sweak.teacherhelper.databinding.StudentActivityItemBinding

class StudentActivitiesAdapter(
    private val context: Context,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : ListAdapter<StudentActivity, StudentActivitiesAdapter.StudentActivityHolder>(DIFF_CALLBACK) {

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
            with (getItem(holder.absoluteAdapterPosition)) {
                binding.textViewStudentActivityType.text = when (this.activityType) {
                    StudentActivity.MISSING_KIT_ACTIVITY_TYPE ->
                        context.getString(R.string.missing_kit)
                    else ->
                        context.getString(R.string.activity) + ": " + this.activityType
                }

                when (this.activityType) {
                    StudentActivity.MISSING_KIT_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.rgb(255, 255, 128))
                    StudentActivity.PLUS_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.rgb(128, 255, 128))
                    StudentActivity.MINUS_ACTIVITY_TYPE ->
                        binding.root.setBackgroundColor(Color.rgb(255, 128, 128))
                }

                binding.textViewStudentActivityOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    fun getStudentAt(position: Int): StudentActivity = getItem(position)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<StudentActivity> =
            object : DiffUtil.ItemCallback<StudentActivity>() {
                override fun areItemsTheSame(oldItem: StudentActivity, newItem: StudentActivity): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(oldItem: StudentActivity, newItem: StudentActivity): Boolean {
                    return (oldItem.activityType == newItem.activityType) and
                            (oldItem.studentId == newItem.studentId)
                }
            }
    }
}