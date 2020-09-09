package com.neighbourschef.customer.ui.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neighbourschef.customer.ui.fragment.menu.MenuFragment
import com.neighbourschef.customer.util.common.PATH_REST_OF_THE_WEEK
import com.neighbourschef.customer.util.common.PATH_TODAY
import com.neighbourschef.customer.util.common.PATH_TOMORROW
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MenuFragment.newInstance(PATH_TODAY)
        1 -> MenuFragment.newInstance(PATH_TOMORROW)
        2 -> MenuFragment.newInstance(PATH_REST_OF_THE_WEEK)
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getItemCount(): Int = 3
}
