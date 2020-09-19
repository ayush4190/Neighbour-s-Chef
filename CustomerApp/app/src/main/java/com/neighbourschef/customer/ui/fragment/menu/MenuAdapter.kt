package com.neighbourschef.customer.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardFoodBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.fragment.FoodViewHolder
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT

class MenuAdapter(
    items: MutableList<Product>,
    private val day: String,
    private val navController: NavController
): BaseAdapter<FoodViewHolder, Product>(items, true) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
        FoodViewHolder(
            CardFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListener {
                navController.navigate(
                    R.id.navigate_to_item_detail,
                    bundleOf(EXTRA_PRODUCT to items[adapterPosition])
                )
            }
        }
}
