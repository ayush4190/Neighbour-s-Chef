package com.example.myapplication.ui.fragment.cart

import android.widget.Toast
import com.example.myapplication.databinding.CardviewCartBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder

class CartViewHolder(binding: CardviewCartBinding): BaseViewHolder<CardviewCartBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.cartBtnMinus.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "decrement",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.cartBtnPlus.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "increment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}