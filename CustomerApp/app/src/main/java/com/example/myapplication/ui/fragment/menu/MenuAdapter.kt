package com.example.myapplication.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.example.myapplication.databinding.CardviewFoodBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuAdapter(
    items: MutableList<Product>,
    private val navController: NavController
): BaseAdapter<MenuViewHolder, Product>(items) {
    private val s: List<String>? = null

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            CardviewFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            navController
        )

}