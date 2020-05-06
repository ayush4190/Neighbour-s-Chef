package com.example.myapplication.ui.fragment.menu.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.TodaysMenufragBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.menu.MenuAdapter
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.State
import com.example.myapplication.util.common.State.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

class TodaysMenuFragment :
    BaseFragment<TodaysMenufragBinding?>() {
    private val products: List<Product> = ArrayList()
    private var adapter: MenuAdapter? = null
    @ExperimentalCoroutinesApi
    private var viewModel: TodayMenuViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TodaysMenufragBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodayMenuViewModel::class.java)
        adapter = MenuAdapter(
            products,
            requireActivity().supportFragmentManager
        )
        adapter!!.setHasStableIds(true)
        binding!!.recyclerviewTodaymenu.adapter = adapter
        binding!!.recyclerviewTodaymenu.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerviewTodaymenu.setHasFixedSize(false)
        observeChanges()
    }

    @ExperimentalCoroutinesApi
    private fun observeChanges() {
        viewModel!!.products.observe(
            viewLifecycleOwner,
            Observer { state: State? ->
                if (state is Loading) {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                } else if (state is State.Success<*>) {
                    val data =
                        state.data as List<*>?
                    adapter!!.submitList(data!! as List<Product>, true)
                } else if (state is State.Failure) {
                    Toast.makeText(
                        requireContext(),
                        state.reason,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    companion object {
        fun newInstance(): TodaysMenuFragment {
            return TodaysMenuFragment()
        }
    }
}