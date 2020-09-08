package com.neighbourschef.customer.ui.fragment.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentHomeBinding
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.restartApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance


@ExperimentalCoroutinesApi
class HomeFragment: BaseFragment<FragmentHomeBinding>(), DIAware {
    override val di by di()
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
        currentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
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
                findNavController().navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                app.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}