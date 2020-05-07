package com.example.myapplication.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.util.android.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SectionsPagerAdapter(this)
        binding.ViewPageHomePager.adapter = adapter
        TabLayoutMediator(binding.homeTabLayout, binding.ViewPageHomePager) { tab, position ->
            val titles = arrayOf("Today's Menu", "Tomorrow's Menu", "Rest of the Week")
            tab.text = titles[position]
        }.attach()
    }
}