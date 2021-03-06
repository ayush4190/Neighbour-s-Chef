package com.neighbourschef.customer.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentHomeBinding
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.common.DAY_TODAY
import com.neighbourschef.customer.util.common.DAY_TOMORROW
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentHomeBinding.inflate(inflater, container, false)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_profile)!!
        val imageView = menuItem.actionView?.findViewById<ImageView>(R.id.img_user_account)
        menuItem.actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        imageView?.load(auth.currentUser?.photoUrl) {
            transformations(
                CircleCropTransformation(),
                CircleBorderTransformation(
                    borderColor = ContextCompat.getColor(requireContext(), R.color.colorOnPrimary)
                )
            )
            placeholder(R.drawable.ic_person_outline_24)
            fallback(R.drawable.ic_person_outline_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_profile -> {
                navController.navigate(MobileNavigationDirections.navigateToProfile())
                true
            }
            R.id.action_help -> {
                navController.navigate(MobileNavigationDirections.navigateToHelp())
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                (requireActivity() as MainActivity).googleSignInClient.signOut()
                navController.navigate(MobileNavigationDirections.navigateToRegistration())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
