package com.sweak.teacherhelper.ui.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sweak.teacherhelper.R
import com.sweak.teacherhelper.databinding.ActivityMainBinding
import com.sweak.teacherhelper.ui.adapter.viewpager.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareViewPager(savedInstanceState)
    }

    private fun prepareViewPager(savedInstanceState: Bundle?) {
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, lifecycle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        if (savedInstanceState == null)
            viewPager.setCurrentItem(1, false)

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): CharSequence {
        return when (position) {
            0 -> getString(R.string.notes)
            1 -> getString(R.string.schedule)
            2 -> getString(R.string.groups)
            else -> ""
        }
    }
}