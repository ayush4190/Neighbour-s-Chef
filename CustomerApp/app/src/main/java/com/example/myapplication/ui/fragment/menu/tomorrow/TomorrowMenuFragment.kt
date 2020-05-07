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
import com.example.myapplication.databinding.FragmentTomorrowTabBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.menu.MenuAdapter
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TomorrowMenuFragment: BaseFragment<FragmentTomorrowTabBinding>() {
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
        _binding = FragmentTomorrowTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerviewTomorrowmenu.apply {
            adapter = this@TomorrowMenuFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                is State.Success<*> -> {
                    it.data as List<Product>
                    adapter.submitList(it.data)
                }
                is State.Failure -> Toast.makeText(requireContext(), it.reason, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance(): TomorrowMenuFragment = TomorrowMenuFragment()
    }
}