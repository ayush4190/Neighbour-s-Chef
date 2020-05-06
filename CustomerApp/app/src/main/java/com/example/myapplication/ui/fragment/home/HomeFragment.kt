package com.example.myapplication.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.util.android.base.BaseFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment :
    BaseFragment<FragmentHomeBinding?>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            SectionsPagerAdapter(childFragmentManager)
        binding!!.homeTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        binding!!.ViewPageHomePager.adapter = adapter
        binding!!.homeTabLayout.setupWithViewPager(binding!!.ViewPageHomePager)
        binding!!.ViewPageHomePager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding!!.homeTabLayout.setScrollPosition(position, 0f, true)
                binding!!.homeTabLayout.isSelected = true
                binding!!.ViewPageHomePager.parent.requestDisallowInterceptTouchEvent(true)
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}