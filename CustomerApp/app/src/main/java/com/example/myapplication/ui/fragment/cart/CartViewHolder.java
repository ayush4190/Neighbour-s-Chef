package com.example.myapplication.ui.fragment.cart;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.CardviewCartBinding;
import com.example.myapplication.model.Cart;
import com.example.myapplication.util.android.base.BaseViewHolder;

class CartViewHolder extends BaseViewHolder<CardviewCartBinding, Cart> {
    CartViewHolder(@NonNull CardviewCartBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Cart item) {
        binding.cartBtnMinus.setOnClickListener(view -> Toast.makeText(binding.getRoot().getContext(),"decrement",Toast.LENGTH_SHORT).show());

        binding.cartBtnPlus.setOnClickListener(view -> Toast.makeText(binding.getRoot().getContext(),"increment", Toast.LENGTH_SHORT).show());
    }
}
