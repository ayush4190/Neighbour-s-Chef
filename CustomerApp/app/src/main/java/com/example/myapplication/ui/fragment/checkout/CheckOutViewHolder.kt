package com.example.myapplication.ui.fragment.checkout

import com.example.myapplication.R
import com.example.myapplication.databinding.CardviewCheckoutBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder

internal class CheckOutViewHolder(binding: CardviewCheckoutBinding) : BaseViewHolder<CardviewCheckoutBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.checkoutName.text = item.name
        binding.checkoutPrice.text = binding.root.context.getString(R.string.set_price, item.price)
        binding.checkoutQuantity.text = item.quantity.toString()
    }
}