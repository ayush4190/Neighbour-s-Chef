package com.neighbourschef.vendor.ui.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.vendor.databinding.FragmentOrdersBinding
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.Result
import com.neighbourschef.vendor.util.common.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    private val viewModel: OrdersViewModel by viewModels()

    private val adapter: OrdersAdapter by lazy(LazyThreadSafetyMode.NONE) {
        OrdersAdapter(mutableListOf(), navController)
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
            addVeiledItems(10)
        }

        lifecycleScope.launch {
            binding.recyclerOrders.veil()
            viewModel.orders.collectLatest {
                when (it) {
                    is Result.Value -> {
                        binding.recyclerOrders.unVeil()
                        adapter.submitList(it.value)
                    }
                    is Result.Error -> {
                        binding.recyclerOrders.unVeil()
                        it.error.log()
                        toast { it.error.message ?: it.error.toString() }
                    }
                }
            }
        }
    }
}
