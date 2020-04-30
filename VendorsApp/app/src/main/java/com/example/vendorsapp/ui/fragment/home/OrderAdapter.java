package com.example.vendorsapp.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.vendorsapp.databinding.OrderViewBinding;
import com.example.vendorsapp.model.OrderDetail;
import com.example.vendorsapp.util.android.base.BaseAdapter;

import java.util.List;

public class OrderAdapter extends BaseAdapter<OrderViewHolder, OrderDetail> {
    private final Context context;
    private List<String> s;

    OrderAdapter(final List<OrderDetail> items, final Context context) {
        super(items);
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(
                OrderViewBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                ),
                context
        );
    }
}
