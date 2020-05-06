package com.example.myapplication.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment
import com.example.myapplication.ui.fragment.menu.today.TodaysMenuFragment
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class SectionsPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> TodaysMenuFragment.newInstance()
        1 -> TomorrowMenuFragment.newInstance()
        2 -> RestoftheWeekFragment.newInstance()
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "Today's Menu"
        1 -> "Tomorrow's Menu "
        2 -> "Rest of the Week "
        else -> throw  IllegalArgumentException("Position $position is out of bounds!")
    }
}