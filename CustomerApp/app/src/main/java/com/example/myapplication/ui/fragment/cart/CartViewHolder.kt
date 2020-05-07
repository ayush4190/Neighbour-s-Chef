package com.example.myapplication.ui.fragment.cart

import com.example.myapplication.databinding.CardviewCartBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder

class CartViewHolder(binding: CardviewCartBinding): BaseViewHolder<CardviewCartBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.cartBtnMinus.setOnClickListener {
            if (item.quantity == 1) {

            }
        }
        binding.cartBtnPlus.setOnClickListener {
            item.quantity++
        }
    }
}