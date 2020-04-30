package com.example.myapplication.ui.fragment.checkout;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myapplication.databinding.CardviewCheckoutBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.util.android.base.BaseAdapter;

import java.util.List;

public class CheckOutAdapter extends BaseAdapter<CheckOutViewHolder, Product> {
    private List<String> s;

    CheckOutAdapter(final List<Product> items) {
        super(items);
    }

    @NonNull
    @Override
    public CheckOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckOutViewHolder(CardviewCheckoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }
}