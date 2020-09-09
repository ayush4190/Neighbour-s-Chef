package com.neighbourschef.customer.ui.fragment.menu.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.customer.databinding.FragmentTodayMenuBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.fragment.menu.MenuAdapter
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.State
import com.neighbourschef.customer.util.common.State.Loading
import com.neighbourschef.customer.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TodayMenuFragment: BaseFragment<FragmentTodayMenuBinding>() {
    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), findNavController())
    }
    private val viewModel: TodayMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentTodayMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMenu.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext()))
            addVeiledItems(VEILED_ITEM_COUNT)
            getRecyclerView().setHasFixedSize(true)
        }
        observeChanges()
    }

    @Suppress("UNCHECKED_CAST")
    private fun observeChanges() {
        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> binding.recyclerMenu.veil()
                is State.Success<*> -> {
                    binding.recyclerMenu.unVeil()
                    adapter.submitList(it.data as List<Product>)
                }
                is State.Failure -> {
                    binding.recyclerMenu.unVeil()
                    toast(it.reason)
                }
            }
        }
    }

    companion object {
        fun newInstance(): TodayMenuFragment = TodayMenuFragment()
    }
}
