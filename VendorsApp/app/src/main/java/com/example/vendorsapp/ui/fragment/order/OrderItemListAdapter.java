package com.example.vendorsapp.ui.fragment.order;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorsapp.databinding.OrderDetailsBinding;
import com.example.vendorsapp.model.Product;
import com.example.vendorsapp.ui.fragment.home.OrderAdapter;

import java.util.List;

public class OrderItemListAdapter  extends RecyclerView.Adapter<OrderItemListViewHolder> implements View.OnClickListener  {
    private List<Product> products;
    private List<String> s;
    public String TAG = "TODAYSMENU";

    OrderItemListAdapter(List<Product> products) {
        this.products = products;
    }

    void clear() {
        products.clear();
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OrderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OrderAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }
    }

    @NonNull
    @Override
    public OrderItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderDetailsBinding binding = OrderDetailsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        binding.getRoot().setOnClickListener(this);
        return new OrderItemListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListViewHolder holder, int position) {
        holder.bindTo(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}