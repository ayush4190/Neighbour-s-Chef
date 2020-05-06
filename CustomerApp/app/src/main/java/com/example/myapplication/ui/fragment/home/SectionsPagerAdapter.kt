package com.example.myapplication.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment
import com.example.myapplication.ui.fragment.menu.today.TodaysMenuFragment
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuFragment

internal class SectionsPagerAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TodaysMenuFragment.newInstance()
            1 -> TomorrowMenuFragment.newInstance()
            2 -> RestoftheWeekFragment.newInstance()
            else -> throw IllegalArgumentException("Position $position is out of bounds!")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Today's Menu"
            1 -> return "Tomorrow's Menu "
            2 -> return "Rest of the Week "
            else -> {
            }
        }
        return null
    }
}