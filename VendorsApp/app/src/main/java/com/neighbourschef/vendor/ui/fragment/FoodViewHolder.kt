package com.neighbourschef.vendor.ui.fragment

import coil.load
import coil.transform.CircleCropTransformation
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.CardFoodBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.util.android.base.BaseViewHolder

class FoodViewHolder(
    binding: CardFoodBinding
): BaseViewHolder<CardFoodBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.textFoodName.text = item.name

        val priceText = if (item.quantity == 0) {
            String.format("%.2f", item.price)
        } else {
            String.format("%.2f x %s", item.price, item.quantity)
        }
        binding.textFoodPrice.text = binding.root.context.getString(R.string.set_price, priceText)

        // To be changed eventually
        binding.imgFood.load(R.drawable.food_sample)
        binding.textFoodDescription.text = binding.root.context.getString(R.string.food_description_placeholder)

        val drawable = if (item.veg) R.drawable.green_veg else R.drawable.red_non_veg
        binding.imgFoodVegNonVeg.load(drawable) {
            transformations(CircleCropTransformation())
        }
    }
}
