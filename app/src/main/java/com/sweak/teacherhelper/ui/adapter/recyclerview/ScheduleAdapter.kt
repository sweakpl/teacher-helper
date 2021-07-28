package com.sweak.teacherhelper.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.database.entity.Schedule
import com.sweak.teacherhelper.databinding.ScheduleItemBinding

class ScheduleAdapter(
    private val context: Context,
    private var optionsMenuClickListener: OptionsMenuClickListener
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>() {

    private var schedule: List<Schedule> = ArrayList()

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
            with (schedule[position]) {
                binding.textViewScheduleActivity.text = this.activity
                binding.textViewScheduleTime.text =
                    context.getString(R.string.schedule_time_template, this.timeStart,this.timeEnd)

                binding.textViewScheduleOptions.setOnClickListener {
                    optionsMenuClickListener.onOptionsMenuClicked(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = schedule.size

    fun setSchedule(schedule: List<Schedule>) {
        this.schedule = schedule
        notifyDataSetChanged()
    }

    fun getScheduleAt(position: Int): Schedule = schedule[position]
}