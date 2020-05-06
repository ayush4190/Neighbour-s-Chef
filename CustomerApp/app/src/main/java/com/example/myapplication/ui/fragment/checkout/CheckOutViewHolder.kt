package com.example.myapplication.ui.fragment.checkout

import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.CardviewCheckoutBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder
import com.example.myapplication.util.android.log

internal class CheckOutViewHolder(binding: CardviewCheckoutBinding) : BaseViewHolder<CardviewCheckoutBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        item.log()
        binding.root
            .setOnClickListener { v: View ->
                Toast.makeText(
                    v.context,
                    item.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}