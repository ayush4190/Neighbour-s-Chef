package com.neighbourschef.vendor.ui.fragment.menu.root

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neighbourschef.vendor.ui.fragment.menu.MenuFragment
import com.neighbourschef.vendor.util.common.DAY_TODAY
import com.neighbourschef.vendor.util.common.DAY_TOMORROW
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MenuFragment.newInstance(DAY_TODAY)
        1 -> MenuFragment.newInstance(DAY_TOMORROW)
        // 2 -> MenuFragment.newInstance(PATH_REST_OF_THE_WEEK)
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getItemCount(): Int = 2
}
