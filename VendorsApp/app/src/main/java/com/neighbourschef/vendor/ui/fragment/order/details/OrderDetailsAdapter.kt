package com.neighbourschef.vendor.ui.fragment.order.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.neighbourschef.vendor.databinding.CardFoodBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.ui.fragment.FoodViewHolder
import com.neighbourschef.vendor.util.android.base.BaseAdapter

class OrderDetailsAdapter(items: MutableList<Product>) : BaseAdapter<FoodViewHolder, Product>(items, true) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            CardFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
