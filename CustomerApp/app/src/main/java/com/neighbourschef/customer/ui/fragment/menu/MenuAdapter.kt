package com.neighbourschef.customer.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import coil.load
import coil.transform.CircleCropTransformation
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardFoodBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.base.BaseViewHolder
import com.neighbourschef.customer.util.common.EXTRA_DAY
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuAdapter(
    items: MutableList<Product>,
    private val day: String,
    private val navController: NavController
): BaseAdapter<MenuAdapter.MenuViewHolder, Product>(items, true) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            CardFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListener {
                navController.navigate(
                    R.id.navigate_to_item_detail,
                    bundleOf(
                        EXTRA_PRODUCT to items[adapterPosition],
                        EXTRA_DAY to day
                    )
                )
            }
        }

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

            val drawable = if (item.veg) R.drawable.green_veg else R.drawable.red_non_veg
            binding.imgFoodVegNonVeg.load(drawable) {
                transformations(CircleCropTransformation())
            }
        }
    }
}
