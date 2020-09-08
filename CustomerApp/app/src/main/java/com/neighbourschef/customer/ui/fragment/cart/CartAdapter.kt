package com.neighbourschef.customer.ui.fragment.cart

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardCartBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.model.total
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.saveCart

class CartAdapter(
    items: MutableList<Product>,
    private val sharedPreferences: SharedPreferences,
    private val priceTextView: MaterialTextView
): BaseAdapter<CartViewHolder, Product>(items, false) {
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