package com.example.myapplication.ui.activity.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.myapplication.ui.fragment.login.SignInFragment
import com.example.myapplication.ui.fragment.register.SignUpFragment

internal class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> SignInFragment.newInstance()
        1 -> SignUpFragment.newInstance()
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "Sign in"
        1 -> "Sign up"
        else -> throw IllegalArgumentException("Position $position is out of bounds!")
    }
}