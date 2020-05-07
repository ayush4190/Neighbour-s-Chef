package com.example.myapplication.ui.fragment.menu.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.TodaysMenufragBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.menu.MenuAdapter
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.State
import com.example.myapplication.util.common.State.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TodaysMenuFragment: BaseFragment<TodaysMenufragBinding>() {
    private val adapter: MenuAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MenuAdapter(mutableListOf(), findNavController()).also {
            it.setHasStableIds(true)
        }
    }
    private val viewModel: TodayMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TodaysMenufragBinding.inflate(inflater, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewTodaymenu.apply {
            adapter = this@TodaysMenuFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        observeChanges()
    }

    @ExperimentalCoroutinesApi
    private fun observeChanges() {
        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                is State.Success<*> -> {
                    it.data as List<Product>
                    adapter.submitList(it.data, true)
                }
                is State.Failure -> Toast.makeText(requireContext(), it.reason, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(): TodaysMenuFragment = TodaysMenuFragment()
    }
}