package com.example.myapplication.ui.fragment.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.example.myapplication.util.android.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SectionsPagerAdapter(this)
        binding.container.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.container) { tab, position ->
            val titles = arrayOf("Sign In", "Sign Up")
            tab.text = titles[position]
        }.attach()
    }
}