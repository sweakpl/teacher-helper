package com.sweak.teacherhelper.features.dayschedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.data.database.entity.Schedule
import com.sweak.teacherhelper.databinding.ScheduleItemBinding

class ScheduleAdapter(
    private val context: Context,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : ListAdapter<Schedule, ScheduleAdapter.ScheduleHolder>(DIFF_CALLBACK) {

    class ScheduleHolder(val binding: ScheduleItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHolder {
        val binding = ScheduleItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleHolder, position: Int) {
        with (holder) {
            with (getItem(holder.absoluteAdapterPosition)) {
                binding.textViewScheduleActivity.text = this.activity
                binding.textViewScheduleTime.text =
                    context.getString(R.string.schedule_time_template, this.timeStart,this.timeEnd)

                binding.textViewScheduleOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(holder.absoluteAdapterPosition)
                }
            }
        }
    }

    fun getScheduleAt(position: Int): Schedule = getItem(position)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Schedule> =
            object : DiffUtil.ItemCallback<Schedule>() {
                override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                    return oldItem.id == newItem.id
                }
                override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                    return (oldItem.activity == newItem.activity) and
                            (oldItem.day == newItem.day) and
                            (oldItem.timeStart == newItem.timeStart) and
                            (oldItem.timeEnd == newItem.timeEnd)
                }
            }
    }
}