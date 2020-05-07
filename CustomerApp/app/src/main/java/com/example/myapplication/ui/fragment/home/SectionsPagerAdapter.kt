package com.example.myapplication.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment
import com.example.myapplication.ui.fragment.menu.today.TodaysMenuFragment
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> TodaysMenuFragment.newInstance()
        1 -> TomorrowMenuFragment.newInstance()
        2 -> RestoftheWeekFragment.newInstance()
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getItemCount(): Int = 3
}