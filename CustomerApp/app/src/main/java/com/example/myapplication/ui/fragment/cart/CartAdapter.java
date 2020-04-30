package com.example.myapplication.ui.fragment.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CardviewCartBinding;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private int cartQuantity ;
    private TextView mTextView;

    CartAdapter() {}

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(
                CardviewCartBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        holder.bindTo(null);
    }

    @Override
    public int getItemCount() {
        return 1;
//                ShoppingCartItem.getInstance(mContext).getFoodTypeSize();
    }

    public void deleteData(int position) {

    }
}

