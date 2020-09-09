package com.neighbourschef.customer.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.customer.databinding.FragmentMenuBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.EXTRA_DAY
import com.neighbourschef.customer.util.common.State
import com.neighbourschef.customer.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), findNavController())
    }
    private val viewModel: MenuViewModel by viewModels { MenuViewModelFactory(day) }

    private val day: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_DAY] as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentMenuBinding.inflate(inflater, container, false)
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

    private fun observeChanges() {
        viewModel.items.observe(viewLifecycleOwner) {
            binding.recyclerMenu.isVisible = it !is State.Failure

            when (it) {
                is State.Loading -> binding.recyclerMenu.veil()
                is State.Success<*> -> {
                    binding.recyclerMenu.unVeil()
                    adapter.submitList(it.data as List<Product>)

                    binding.textEmptyState.isVisible = adapter.itemCount == 0
                    binding.recyclerMenu.isVisible = adapter.itemCount != 0
                }
                is State.Failure -> {
                    binding.textEmptyState.isVisible = true
                    toast(it.reason)
                }
            }
        }
    }

    companion object {
        fun newInstance(day: String) = MenuFragment().apply {
            arguments = bundleOf(EXTRA_DAY to day)
        }
    }
}
