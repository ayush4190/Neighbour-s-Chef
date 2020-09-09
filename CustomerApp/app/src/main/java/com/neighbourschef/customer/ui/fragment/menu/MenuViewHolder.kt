package com.neighbourschef.customer.ui.fragment.menu

import coil.load
import coil.transform.CircleCropTransformation
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardFoodBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.base.BaseViewHolder
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
        binding.imgFoodVegNonVeg.load(R.drawable.green_veg) {
            transformations(CircleCropTransformation())
        }
    }
}
