package com.example.myapplication.ui.fragment.cart

import android.view.View
import coil.api.load
import com.example.myapplication.R
import com.example.myapplication.databinding.CardCartBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder

class CartViewHolder(binding: CardCartBinding): BaseViewHolder<CardCartBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.textFoodName.text = item.name
        binding.textFoodPrice.text = binding.root.context.getString(R.string.set_price, item.price)
        binding.textFoodQuantity.text = item.quantity.toString()

        // To be changed eventually
        binding.imgFood.load(R.drawable.food_sample)
        binding.textFoodDescription.text = binding.root.context.getString(R.string.food_description_placeholder)
        binding.imgFoodVegNonVeg.load(R.color.green_veg)
    }

    fun setMinusClickListener(listener: ((View) -> Unit)?) =
        binding.btnMinus.setOnClickListener(listener)

    fun setPlusClickListener(listener: ((View) -> Unit)?) =
        binding.btnPlus.setOnClickListener(listener)
}