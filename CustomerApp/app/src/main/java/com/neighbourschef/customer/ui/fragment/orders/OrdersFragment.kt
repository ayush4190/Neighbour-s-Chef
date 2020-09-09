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
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentOrdersBinding
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.restartApp
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class OrdersFragment: BaseFragment<FragmentOrdersBinding>(), DIAware {
    override val di by di()
    val database by instance<CustomerDatabase>()
    val app by instance<CustomerApp>()

    private val ordersViewModel by viewModels<OrdersViewModel> { OrdersViewModelFactory(database) }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { OrdersAdapter(mutableListOf(), database) }

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
            adapter.submitList(it)
            binding.recyclerOrders.isVisible = adapter.itemCount != 0
            binding.textEmptyState.isVisible = adapter.itemCount == 0
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
