package com.example.myapplication.ui.fragment.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.myapplication.CustomerApp
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.getCart
import com.example.myapplication.util.android.toast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class HomeFragment: BaseFragment<FragmentHomeBinding>(), KodeinAware {
    override val kodein by kodein()
    val app by instance<CustomerApp>()
    val sharedPreferences by instance<SharedPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onResume() {
        super.onResume()

        val cart = getCart(sharedPreferences)
        (requireActivity() as MainActivity).binding.layoutAppBar.fab.text = if (cart.isEmpty()) {
            ""
        } else {
            getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                /*
                    TODO: Navigate to settings
                    user: aayush
                    date: 7/5/20
                */
                true
            }
            R.id.action_logout -> {
                signOut()
                findNavController().navigate(MobileNavigationDirections.navigateToRegistration())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun signOut() {
        app.googleSignInClient.signOut()
            .addOnCompleteListener {
                app.account = null
            }
            .addOnFailureListener {
                toast(requireContext(), "Unable to sign out. error=${it.message}")
            }
    }
}