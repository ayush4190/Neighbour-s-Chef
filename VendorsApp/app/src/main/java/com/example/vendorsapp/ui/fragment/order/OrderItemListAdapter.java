package com.example.vendorsapp.ui.fragment.order;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.vendorsapp.databinding.OrderDetailsBinding;
import com.example.vendorsapp.model.Product;
import com.example.vendorsapp.util.android.base.BaseAdapter;

import java.util.List;

public class OrderItemListAdapter  extends BaseAdapter<OrderItemListViewHolder, Product> {
    private List<String> s;

    OrderItemListAdapter(List<Product> items) {
        super(items);
    }

    @NonNull
    @Override
    public OrderItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemListViewHolder(OrderDetailsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }
}