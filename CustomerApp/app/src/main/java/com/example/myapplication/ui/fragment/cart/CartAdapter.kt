package com.example.myapplication.ui.fragment.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.databinding.CardviewCartBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseAdapter

internal class CartAdapter(items: MutableList<Product>): BaseAdapter<CartViewHolder, Product>(items) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            CardviewCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}