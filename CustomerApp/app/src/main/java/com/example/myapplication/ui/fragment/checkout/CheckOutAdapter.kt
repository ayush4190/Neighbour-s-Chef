package com.example.myapplication.ui.fragment.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.databinding.CardviewCheckoutBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseAdapter

internal class CheckOutAdapter(items: MutableList<Product>): BaseAdapter<CheckOutViewHolder, Product>(items) {
    private val s: List<String>? = null

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckOutViewHolder =
        CheckOutViewHolder(
            CardviewCheckoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}