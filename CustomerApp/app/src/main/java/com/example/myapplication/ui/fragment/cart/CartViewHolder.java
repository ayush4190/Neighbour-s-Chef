package com.example.myapplication.ui.fragment.cart;

import android.view.View;
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
        binding.cartBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(binding.getRoot().getContext(),"decrement",Toast.LENGTH_SHORT).show();
            }
        });

        binding.cartBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(binding.getRoot().getContext(),"increment",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
