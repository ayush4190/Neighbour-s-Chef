package com.neighbourschef.customer.ui.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentOrdersBinding
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class OrdersFragment: BaseFragment<FragmentOrdersBinding>() {
    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    private val ordersViewModel by viewModels<OrdersViewModel> { OrdersViewModelFactory(auth.currentUser!!.uid) }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { OrdersAdapter(mutableListOf(), auth.currentUser!!.uid) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerOrders.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerOrders.adapter = adapter

        binding.recyclerOrders.isVisible = adapter.itemCount != 0
        binding.textEmptyState.isVisible = adapter.itemCount == 0

        ordersViewModel.orders.observe(viewLifecycleOwner) {
            binding.recyclerOrders.isVisible = adapter.itemCount != 0
            binding.textEmptyState.isVisible = adapter.itemCount == 0
            when (it) {
                is UiState.Loading -> binding.progressBar.isVisible = true
                is UiState.Success<*> -> {
                    binding.progressBar.isVisible = false
                    adapter.submitList(it.data as List<Order>)
                }
                is UiState.Failure -> {
                    binding.progressBar.isVisible = false
                    toast(it.reason)
                }
            }
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
                auth.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
