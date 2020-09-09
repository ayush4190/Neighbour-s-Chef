package com.neighbourschef.vendor.ui.fragment.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.neighbourschef.vendor.databinding.OrderDetailsBinding;
import com.neighbourschef.vendor.model.Product;
import com.neighbourschef.vendor.util.android.base.BaseViewHolder;

class OrderItemListViewHolder extends BaseViewHolder<OrderDetailsBinding, Product> {
    OrderItemListViewHolder(@NonNull OrderDetailsBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Product item) {

    }
}
