package com.neighbourschef.vendor.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.neighbourschef.vendor.databinding.FragmentHomepageBinding
import com.neighbourschef.vendor.model.OrderDetail
import com.neighbourschef.vendor.util.android.base.BaseFragment
import java.util.ArrayList

class HomepageFragment private constructor() : BaseFragment<FragmentHomepageBinding?>() {
    private val orders: List<OrderDetail> = ArrayList()
    private var adapter: OrderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrderAdapter(orders.toMutableList())
        binding!!.recyclerviewOrder.adapter = adapter
        binding!!.recyclerviewOrder.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerviewOrder.setHasFixedSize(false)
    }

    companion object {
        fun newInstance(): HomepageFragment = HomepageFragment()
    }
}
