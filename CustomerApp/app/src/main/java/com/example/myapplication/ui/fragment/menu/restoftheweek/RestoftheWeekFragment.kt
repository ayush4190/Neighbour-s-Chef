package com.example.myapplication.ui.fragment.menu.restoftheweek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentRestOfTheWeekTabBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.menu.MenuAdapter
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.State
import com.example.myapplication.util.common.State.Loading
import java.util.*

class RestoftheWeekFragment :
    BaseFragment<FragmentRestOfTheWeekTabBinding?>() {
    private val products: List<Product> = ArrayList()
    private var adapter: MenuAdapter? = null
    private var viewModel: RestOfTheWeekViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestOfTheWeekTabBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(RestOfTheWeekViewModel::class.java)
        adapter = MenuAdapter(
            products,
            requireActivity().supportFragmentManager
        )
        adapter!!.setHasStableIds(true)
        binding!!.recyclerviewRestoftheWeekMenu.adapter = adapter
        binding!!.recyclerviewRestoftheWeekMenu.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerviewRestoftheWeekMenu.setHasFixedSize(false)
        observeChanges()
    }

    private fun observeChanges() {
        viewModel!!.products.observe(
            viewLifecycleOwner,
            Observer { state: State? ->
                if (state is Loading) {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                } else if (state is State.Success<*>) {
                    val data =
                        state.data as List<Product>?
                    adapter!!.submitList(data!!, true)
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
        fun newInstance(): RestoftheWeekFragment {
            return RestoftheWeekFragment()
        }
    }
}