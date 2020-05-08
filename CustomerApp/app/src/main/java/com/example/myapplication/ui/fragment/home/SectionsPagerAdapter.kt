package com.example.myapplication.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestOfTheWeekMenuFragment
import com.example.myapplication.ui.fragment.menu.today.TodayMenuFragment
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TodayMenuFragment.newInstance()
        1 -> TomorrowMenuFragment.newInstance()
        2 -> RestOfTheWeekMenuFragment.newInstance()
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getItemCount(): Int = 3
}