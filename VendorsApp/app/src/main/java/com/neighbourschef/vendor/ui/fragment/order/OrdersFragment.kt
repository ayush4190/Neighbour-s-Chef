package com.neighbourschef.vendor.ui.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentOrdersBinding
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.restartApp
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.Result
import com.neighbourschef.vendor.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    private val viewModel: OrdersViewModel by viewModels()

    private val adapter: OrdersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        OrdersAdapter(mutableListOf(), navController)
    }

    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerOrders.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext()))
            addVeiledItems(VEILED_ITEM_COUNT)
        }

        lifecycleScope.launch {
            binding.recyclerOrders.veil()
            viewModel.orders.collectLatest {
                when (it) {
                    is Result.Value -> {
                        binding.recyclerOrders.unVeil()
                        adapter.submitList(it.value)

                        binding.textEmptyState.isVisible = adapter.itemCount == 0
                        binding.recyclerOrders.isVisible = adapter.itemCount != 0
                    }
                    is Result.Error -> {
                        binding.recyclerOrders.unVeil()
                        toast { it.error.message ?: it.error.toString() }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_help -> {
            navController.navigate(MobileNavigationDirections.navigateToHelp())
            true
        }
        R.id.action_logout -> {
            auth.signOut()
            restartApp(requireActivity())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
