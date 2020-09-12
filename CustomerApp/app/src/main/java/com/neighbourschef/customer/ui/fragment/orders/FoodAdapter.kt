package com.neighbourschef.customer.ui.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.neighbourschef.customer.databinding.CardFoodBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.fragment.FoodViewHolder
import com.neighbourschef.customer.util.android.base.BaseAdapter

class FoodAdapter(items: MutableList<Product>) : BaseAdapter<FoodViewHolder, Product>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
        FoodViewHolder(
            CardFoodBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun getItemId(position: Int): Long = items[position].id.hashCode().toLong()
}
