package com.example.myapplication.util.android.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * Base class for RecyclerView ViewHolders
 * @param <VB> ViewBinding class generated for the corresponding layout
 * @param <T> type of item being held
 */
public abstract class BaseViewHolder<VB extends ViewBinding, T> extends RecyclerView.ViewHolder {
    protected VB binding;

    public BaseViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public abstract void bindTo(@Nullable T item);
}
