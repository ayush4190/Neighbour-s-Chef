package com.neighbourschef.customer.ui.fragment.registration

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
internal class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SignInFragment.newInstance()
        1 -> SignUpFragment.newInstance()
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getItemCount(): Int = 2
}