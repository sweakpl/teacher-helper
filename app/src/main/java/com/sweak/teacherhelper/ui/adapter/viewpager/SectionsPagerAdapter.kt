package com.sweak.teacherhelper.ui.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sweak.teacherhelper.ui.fragment.GroupFragment
import com.sweak.teacherhelper.ui.fragment.NoteFragment
import com.sweak.teacherhelper.ui.fragment.WeekScheduleFragment

const val MAIN_SCREEN_PAGE_NUMBER = 3

class SectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> NoteFragment.newInstance()
        1 -> WeekScheduleFragment.newInstance()
        2 -> GroupFragment.newInstance()
        else -> Fragment()
    }

    override fun getItemCount(): Int {
        return MAIN_SCREEN_PAGE_NUMBER
    }
}