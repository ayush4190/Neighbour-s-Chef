package com.neighbourschef.vendor.ui.fragment.menu.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentRootMenuBinding
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.common.DAY_TODAY
import com.neighbourschef.vendor.util.common.DAY_TOMORROW
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RootMenuFragment : BaseFragment<FragmentRootMenuBinding>() {
    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentRootMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            val titles = arrayOf(DAY_TODAY, DAY_TOMORROW)
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_help -> {
            navController.navigate(MobileNavigationDirections.navigateToHelp())
            true
        }
        R.id.action_logout -> {
            auth.signOut()
            navController.navigate(MobileNavigationDirections.navigateToLogin())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
