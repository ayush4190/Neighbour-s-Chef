package com.example.myapplication.ui.fragment.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CardviewHistoryBinding;
import com.example.myapplication.model.Order;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder>{
    private ArrayList<Order> orders;

    HistoryAdapter(ArrayList<Order> orders) {
        this.orders = orders;
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

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.bindTo(orders.get(position));
    }

    public void notifyData(ArrayList<Order> orders) {
//        Log.d("notifyData ", foods.size() + "");
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}

