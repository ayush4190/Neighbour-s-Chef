package com.example.myapplication.ui.fragment.checkout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CardviewCheckoutBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuAdapter;

import java.util.List;

import static com.example.myapplication.util.android.LoggingUtils.log;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutHolder> implements View.OnClickListener{

    private List<Product> products;
    private List<String> s;
    public String TAG = "CHECKOUT ITEMS";

    CheckOutAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        } else {
            log("ERROR", "");
        }
    }

    @NonNull
    @Override
    public CheckOutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       CardviewCheckoutBinding binding =CardviewCheckoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        binding.getRoot().setOnClickListener(this);
        return new CheckOutHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutHolder holder, int position) {
        holder.bindTo(products.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    void clear() {
        products.clear();
        notifyDataSetChanged();
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private TomorrowMenuAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    void setOnItemClickListener(TomorrowMenuAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}