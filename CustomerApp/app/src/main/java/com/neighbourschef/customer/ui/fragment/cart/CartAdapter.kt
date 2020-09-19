package com.neighbourschef.customer.ui.fragment.cart

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textview.MaterialTextView
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardCartBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.model.total
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.base.BaseViewHolder
import com.neighbourschef.customer.util.android.saveCart

class CartAdapter(
    items: MutableList<Product>,
    private val sharedPreferences: SharedPreferences,
    private val priceTextView: MaterialTextView
): BaseAdapter<CartAdapter.CartViewHolder, Product>(items, false) {
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
                priceTextView.text = it.context.getString(R.string.set_price, String.format("%.2f", items.total()))
                saveCart(sharedPreferences, Cart(items))
            }
            setPlusClickListener {
                val item = items[adapterPosition]
                item.quantity++
                bindTo(item)
                priceTextView.text = it.context.getString(R.string.set_price, String.format("%.2f", items.total()))
                saveCart(sharedPreferences, Cart(items))
            }
        }

    class CartViewHolder(binding: CardCartBinding): BaseViewHolder<CardCartBinding, Product>(binding) {
        override fun bindTo(item: Product) {
            binding.textFoodName.text = item.name
            binding.textFoodPrice.text = binding.root.context.getString(
                R.string.set_price,
                String.format("%.2f", item.price)
            )
            binding.textFoodQuantity.text = item.quantity.toString()

            binding.imgFood.load(item.firebaseUrl) {
                placeholder(R.drawable.ic_food_default_64)
                fallback(R.drawable.ic_food_default_64)
            }
            binding.textFoodDescription.text = item.description

            val drawable = if (item.veg) R.drawable.green_veg else R.drawable.red_non_veg
            binding.imgFoodVegNonVeg.load(drawable) {
                transformations(CircleCropTransformation())
            }
        }

        fun setMinusClickListener(listener: ((View) -> Unit)?) =
            binding.btnMinus.setOnClickListener(listener)

        fun setPlusClickListener(listener: ((View) -> Unit)?) =
            binding.btnPlus.setOnClickListener(listener)
    }
}
