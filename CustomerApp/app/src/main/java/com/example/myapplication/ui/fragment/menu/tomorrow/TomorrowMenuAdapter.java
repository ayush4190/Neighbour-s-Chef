package com.example.myapplication.ui.fragment.menu.tomorrow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CardviewFoodBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.menu.MenuViewHolder;

import java.util.List;

public class TomorrowMenuAdapter extends RecyclerView.Adapter<MenuViewHolder> implements View.OnClickListener {
    private List<Product> products;
    private List<String> s;
    public String TAG = "TODAYSMENU";

    TomorrowMenuAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewFoodBinding binding = CardviewFoodBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        binding.getRoot().setOnClickListener(this);
        return new MenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.bindTo(products.get(position));
    }

    void addItem(Product eventsList, String t) {
//        this.s.add(t);
        this.products.add(eventsList);
    }

    @Override
    public int getItemCount() {
        return products.size();
//        foods.size();
    }

    void clear() {
        products.clear();
        notifyDataSetChanged();
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, String.valueOf(view.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }
    }
}