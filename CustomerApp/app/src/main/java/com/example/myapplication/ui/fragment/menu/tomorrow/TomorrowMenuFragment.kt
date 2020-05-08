package com.example.myapplication.ui.fragment.menu.tomorrow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentTomorrowMenuBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.menu.MenuAdapter
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TomorrowMenuFragment: BaseFragment<FragmentTomorrowMenuBinding>() {
    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), findNavController()).also {
            it.setHasStableIds(true)
        }
    }
    private val viewModel: TomorrowMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTomorrowMenuBinding.inflate(inflater, container, false)
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
            addVeiledItems(10)
            getRecyclerView().setHasFixedSize(true)
        }
        observeChanges()
    }

    @Suppress("UNCHECKED_CAST")
    private fun observeChanges() {
        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> binding.recyclerMenu.veil()
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
        fun newInstance(): TomorrowMenuFragment = TomorrowMenuFragment()
    }
}