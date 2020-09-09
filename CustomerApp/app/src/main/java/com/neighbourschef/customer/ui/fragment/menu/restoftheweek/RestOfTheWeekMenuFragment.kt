package com.neighbourschef.customer.ui.fragment.menu.restoftheweek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.customer.databinding.FragmentRestOfTheWeekMenuBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.fragment.menu.MenuAdapter
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.common.State
import com.neighbourschef.customer.util.common.State.Loading
import com.neighbourschef.customer.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RestOfTheWeekMenuFragment: BaseFragment<FragmentRestOfTheWeekMenuBinding>() {
    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), findNavController())
    }
    private val viewModel: RestOfTheWeekViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentRestOfTheWeekMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
                    Toast.makeText(requireContext(), it.reason, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        fun newInstance(): RestOfTheWeekMenuFragment {
            return RestOfTheWeekMenuFragment()
        }
    }
}
