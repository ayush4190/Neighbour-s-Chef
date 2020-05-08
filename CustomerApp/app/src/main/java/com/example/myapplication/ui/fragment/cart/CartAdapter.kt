package com.example.myapplication.ui.fragment.cart

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.CardCartBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Product
import com.example.myapplication.model.total
import com.example.myapplication.util.android.base.BaseAdapter
import com.example.myapplication.util.android.saveCart
import com.google.android.material.textview.MaterialTextView

class CartAdapter(
    items: MutableList<Product>,
    private val sharedPreferences: SharedPreferences,
    private val priceTextView: MaterialTextView
): BaseAdapter<CartViewHolder, Product>(items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            CardCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setMinusClickListener {
                val item = items[adapterPosition]
                if (item.quantity == 1) {
                    items.remove(item)
                    notifyItemRemoved(adapterPosition)
                } else {
                    item.quantity--
                }
                bindTo(item)
                priceTextView.text = it.context.getString(R.string.set_price, items.total())
                saveCart(sharedPreferences, Cart(items))
            }
            setPlusClickListener {
                val item = items[adapterPosition]
                item.quantity++
                bindTo(item)
                priceTextView.text = it.context.getString(R.string.set_price, items.total())
                saveCart(sharedPreferences, Cart(items))
            }
        }
}