package com.example.myapplication.ui.fragment.menu

import coil.api.load
import com.example.myapplication.R
import com.example.myapplication.databinding.CardFoodBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuViewHolder(
    binding: CardFoodBinding
): BaseViewHolder<CardFoodBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.textFoodName.text = item.name
        binding.textFoodPrice.text = binding.root.context.getString(R.string.set_price, item.price)

        // To be changed eventually
        binding.imgFood.load(R.drawable.food_sample)
        binding.textFoodDescription.text = binding.root.context.getString(R.string.food_description_placeholder)
        binding.imgFoodVegNonVeg.load(R.color.green_veg)
    }
}