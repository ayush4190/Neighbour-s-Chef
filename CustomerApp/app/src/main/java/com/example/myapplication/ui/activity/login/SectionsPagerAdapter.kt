package com.example.myapplication.ui.activity.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.ui.fragment.login.SignInFragment
import com.example.myapplication.ui.fragment.register.SignUpFragment

class SectionsPagerAdapter internal constructor(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SignInFragment.newInstance()
            1 -> SignUpFragment.newInstance()
            else -> throw IllegalArgumentException("Position $position is out of bounds!")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Sign in"
            1 -> return "Sign up"
        }
        return null
    }
}