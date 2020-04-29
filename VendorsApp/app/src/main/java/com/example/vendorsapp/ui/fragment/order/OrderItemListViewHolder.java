package com.example.vendorsapp.ui.fragment.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vendorsapp.databinding.OrderDetailsBinding;
import com.example.vendorsapp.model.Product;
import com.example.vendorsapp.util.android.base.BaseViewHolder;

class OrderItemListViewHolder extends BaseViewHolder<OrderDetailsBinding, Product> {
    OrderItemListViewHolder(@NonNull OrderDetailsBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Product item) {

    }
}
