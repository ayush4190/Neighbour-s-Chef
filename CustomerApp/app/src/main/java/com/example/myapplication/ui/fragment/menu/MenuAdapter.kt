package com.example.myapplication.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.databinding.CardFoodBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseAdapter
import com.example.myapplication.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuAdapter(
    items: MutableList<Product>,
    private val navController: NavController
): BaseAdapter<MenuViewHolder, Product>(items, true) {
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
                    bundleOf(EXTRA_PRODUCT to items[adapterPosition])
                )
            }
        }
}