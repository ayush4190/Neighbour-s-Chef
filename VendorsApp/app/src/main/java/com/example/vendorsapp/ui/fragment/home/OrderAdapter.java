package com.example.vendorsapp.ui.fragment.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorsapp.databinding.OrderViewBinding;
import com.example.vendorsapp.model.OrderDetail;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> implements View.OnClickListener  {
    private Context context;
    private List<OrderDetail> orders;
    private List<String> s;
    public String TAG = "TODAYSMENU";
    public static OrderDetail n;

    OrderAdapter(Context context, List<OrderDetail> orders) {
        this.context = context;
        this.orders = orders;
    }

    void clear() {
        orders.clear();
        notifyDataSetChanged();
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
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
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderViewBinding binding = OrderViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        binding.orderView.setOnClickListener(this);
        return new OrderViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bindTo(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
