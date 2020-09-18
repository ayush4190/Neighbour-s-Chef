package com.neighbourschef.vendor.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.databinding.CardFoodBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.ui.fragment.FoodViewHolder
import com.neighbourschef.vendor.util.android.base.BaseAdapter

class MenuAdapter(
    items: MutableList<Product>,
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
                navController.navigate(MobileNavigationDirections.navigateToItemDetails(items[adapterPosition]))
            }
        }
}
