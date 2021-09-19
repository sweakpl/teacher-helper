package com.sweak.teacherhelper.features.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sweak.teacherhelper.features.group.GroupFragment
import com.sweak.teacherhelper.features.note.NoteFragment
import com.sweak.teacherhelper.features.weekschedule.WeekScheduleFragment

const val MAIN_SCREEN_PAGE_NUMBER = 3

class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NoteFragment.newInstance()
        1 -> WeekScheduleFragment.newInstance()
        2 -> GroupFragment.newInstance()
        else -> Fragment()
    }

    override fun getItemCount(): Int {
        return MAIN_SCREEN_PAGE_NUMBER
    }
}