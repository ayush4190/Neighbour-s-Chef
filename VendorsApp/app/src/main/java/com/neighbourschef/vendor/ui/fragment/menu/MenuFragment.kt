package com.neighbourschef.vendor.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.vendor.databinding.FragmentMenuBinding
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.EXTRA_DAY
import com.neighbourschef.vendor.util.common.Result
import com.neighbourschef.vendor.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private val day: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_DAY] as String
    }

    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), navController)
    }
    private val viewModel: MenuViewModel by viewModels { MenuViewModelFactory(day) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerMenu.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext()))
            addVeiledItems(VEILED_ITEM_COUNT)
            getRecyclerView().setHasFixedSize(true)
        }
        observeChanges()
    }

    private fun observeChanges() {
        lifecycleScope.launch {
            viewModel.items.collectLatest {
                binding.recyclerMenu.veil()
                binding.recyclerMenu.isVisible = it is Result.Value

                when (it) {
                    is Result.Value -> {
                        binding.recyclerMenu.unVeil()
                        adapter.submitList(it.value)

                        binding.textEmptyState.isVisible = adapter.itemCount == 0
                        binding.recyclerMenu.isVisible = adapter.itemCount != 0
                    }
                    is Result.Error -> {
                        binding.textEmptyState.isVisible = true
                        toast { it.error.message ?: it.error.toString() }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(day: String): MenuFragment = MenuFragment().apply {
            arguments = bundleOf(EXTRA_DAY to day)
        }
    }
}
