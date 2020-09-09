package com.neighbourschef.vendor.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.neighbourschef.vendor.databinding.OrderViewBinding
import com.neighbourschef.vendor.model.OrderDetail
import com.neighbourschef.vendor.util.android.base.BaseAdapter

class OrderAdapter(items: MutableList<OrderDetail>) :
    BaseAdapter<OrderViewHolder, OrderDetail>(items, false) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            OrderViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}
