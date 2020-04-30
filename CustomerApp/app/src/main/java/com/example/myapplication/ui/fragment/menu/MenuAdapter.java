package com.example.myapplication.ui.fragment.menu;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.databinding.CardviewFoodBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.util.android.base.BaseAdapter;

import java.util.List;

public class MenuAdapter extends BaseAdapter<MenuViewHolder, Product> {
    private final FragmentManager fragmentManager;
    private List<String> s;

    public MenuAdapter(final List<Product> items, final FragmentManager fragmentManager) {
        super(items);
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(
                CardviewFoodBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                ),
                fragmentManager
        );
    }
}