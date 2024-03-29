package com.sweak.teacherhelper.features.weekschedule

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.FragmentWeekScheduleBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeekScheduleFragment : Fragment() {

    private var _binding: FragmentWeekScheduleBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekScheduleBinding.inflate(inflater, container, false)
        val root = binding.root

        prepareViewPager(savedInstanceState)

        return root
    }

    private fun prepareViewPager(savedInstanceState: Bundle?) {
        val sectionsPagerAdapter = SchedulePagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        if (savedInstanceState == null)
            viewPager.setCurrentItem(getCurrentDayIndex(), false)

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        setTabLayoutColors()
    }

    private fun setTabLayoutColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white, null))
            binding.tabLayout.setTabTextColors(
                resources.getColor(R.color.white, null),
                resources.getColor(R.color.white, null)
            )
        } else {
            binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white))
            binding.tabLayout.setTabTextColors(
                resources.getColor(R.color.white), resources.getColor(R.color.white)
            )
        }
    }

    private fun getCurrentDayIndex(): Int =
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            else -> 0
        }

    private fun getTabTitle(position: Int): CharSequence {
        return when (position) {
            0 -> getString(R.string.monday)
            1 -> getString(R.string.tuesday)
            2 -> getString(R.string.wednesday)
            3 -> getString(R.string.thursday)
            4 -> getString(R.string.friday)
            else -> ""
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekScheduleFragment()
    }
}