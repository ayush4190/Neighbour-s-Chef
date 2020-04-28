package com.example.myapplication.ui.fragment.menu;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.CardviewFoodBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.util.android.base.BaseViewHolder;

public class MenuViewHolder extends BaseViewHolder<CardviewFoodBinding, Product> {
    public MenuViewHolder(@NonNull CardviewFoodBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Product item) {
        if (item != null) {
            Log.v("alpha", item.getName());
            binding.foodId.setText(item.getId());
            binding.foodName.setText(item.getName());
            binding.foodPrice.setText(item.getPrice());

//        binding.foodCategory.setText(foods.get(position).getCategory());
//        binding.foodImg.setImageBitmap(foods.get(position).getImage());

            binding.foodImg.setTag(item.getId());
        }
    }
}
