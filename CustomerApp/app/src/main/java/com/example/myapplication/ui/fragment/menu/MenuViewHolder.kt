package com.example.myapplication.ui.fragment.menu

import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.CardviewFoodBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.fragment.details.ItemDetailFragment
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment
import com.example.myapplication.util.android.base.BaseViewHolder
import com.example.myapplication.util.android.log
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuViewHolder(
    binding: CardviewFoodBinding,
    private val fragmentManager: FragmentManager
): BaseViewHolder<CardviewFoodBinding, Product>(binding) {
    override fun bindTo(item: Product) {
        item.log()
        binding.root.setOnClickListener {
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .replace(
                    R.id.main_fragment_container,
                    ItemDetailFragment.newInstance(item)
                )
                .addToBackStack(RestoftheWeekFragment::class.java.name)
                .commit()
        }
        binding.foodId.text = item.id
        binding.foodName.text = item.name
        binding.foodPrice.text = item.price.toString()

//        binding.foodCategory.setText(foods.get(position).getCategory());
//        binding.foodImg.setImageBitmap(foods.get(position).getImage());
        binding.foodImg.tag = item.id
    }
}