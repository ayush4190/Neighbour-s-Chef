package com.example.myapplication.ui.fragment.checkout;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.CardviewCheckoutBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.util.android.base.BaseViewHolder;

public class CheckOutViewHolder extends BaseViewHolder<CardviewCheckoutBinding, Product> {
    CheckOutViewHolder(@NonNull CardviewCheckoutBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Product item) {
        binding.getRoot().setOnClickListener(v ->
                Toast.makeText(v.getContext(), item != null ? item.toString() : "View clicked", Toast.LENGTH_SHORT).show());
    }
}
