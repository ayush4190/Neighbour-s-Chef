package com.example.myapplication.ui.fragment.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myapplication.databinding.CardviewHistoryBinding;
import com.example.myapplication.model.Order;
import com.example.myapplication.util.android.base.BaseAdapter;

import java.util.List;

public class HistoryAdapter extends BaseAdapter<HistoryViewHolder, Order> {
    HistoryAdapter(final List<Order> items) {
        super(items);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(
                CardviewHistoryBinding.inflate(
                       LayoutInflater.from(parent.getContext()),
                       parent,
                       false
                )
        );
    }
}

