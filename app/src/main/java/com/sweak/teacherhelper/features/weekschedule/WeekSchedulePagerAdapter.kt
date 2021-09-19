package com.sweak.teacherhelper.features.weekschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sweak.teacherhelper.features.dayschedule.DayScheduleFragment
import com.sweak.teacherhelper.features.dayschedule.DayScheduleFragment.Companion.FRAGMENT_ARGUMENT_DAY

const val SCHEDULE_SCREEN_PAGE_NUMBER = 5

class SchedulePagerAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        when(position) {
            0 -> bundle.putString(FRAGMENT_ARGUMENT_DAY, MONDAY)
            1 -> bundle.putString(FRAGMENT_ARGUMENT_DAY, TUESDAY)
            2 -> bundle.putString(FRAGMENT_ARGUMENT_DAY, WEDNESDAY)
            3 -> bundle.putString(FRAGMENT_ARGUMENT_DAY, THURSDAY)
            4 -> bundle.putString(FRAGMENT_ARGUMENT_DAY, FRIDAY)
            else -> bundle.putString(FRAGMENT_ARGUMENT_DAY, MONDAY)
        }
        return DayScheduleFragment.newInstance(bundle)
    }

    override fun getItemCount(): Int {
        return SCHEDULE_SCREEN_PAGE_NUMBER
    }

    companion object {
        const val MONDAY = "Mon"
        const val TUESDAY = "Tue"
        const val WEDNESDAY = "Wed"
        const val THURSDAY = "Thu"
        const val FRIDAY = "Fri"
    }
}