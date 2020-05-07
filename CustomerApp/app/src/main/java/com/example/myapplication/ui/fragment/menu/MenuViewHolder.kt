package com.example.myapplication.ui.fragment.menu

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.databinding.CardviewFoodBinding
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.base.BaseViewHolder
import com.example.myapplication.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuViewHolder(
    binding: CardviewFoodBinding,
    private val navController: NavController
): BaseViewHolder<CardviewFoodBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        binding.root.setOnClickListener {
            navController.navigate(
                R.id.navigate_to_item_detail,
                bundleOf(EXTRA_PRODUCT to item)
            )
        }
        binding.foodId.text = item.id
        binding.foodName.text = item.name
        binding.foodPrice.text = item.price.toString()

//        binding.foodCategory.setText(foods.get(position).getCategory());
//        binding.foodImg.setImageBitmap(foods.get(position).getImage());
        binding.foodImg.tag = item.id
    }
}