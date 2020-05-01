package com.example.myapplication.util.android.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

/**
 * Base class for RecyclerView adapters
 * @param <VH> type of RecyclerView.ViewHolder (must extend BaseViewHolder)
 * @param <T> type of item held by the adapter
 */
public abstract class BaseAdapter<VH extends BaseViewHolder<? extends ViewBinding, T>, T> extends RecyclerView.Adapter<VH> {
    protected final List<T> items;

    protected BaseAdapter(final List<T> items) {
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindTo(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Submit a new list of items to be added to the adapter
     * @param newItems list of new items
     * @param overwrite if the existing items must be overwritten or not. If true, items are
     *                  replaced, else they are added
     */
    public void submitList(final List<T> newItems, final boolean overwrite) {
        final int size = items.size();
        if (overwrite) {
            items.clear();
            notifyItemRangeRemoved(0, size);
            items.addAll(newItems);
            notifyItemRangeInserted(0, newItems.size());
        } else {
            items.addAll(newItems);
            notifyItemRangeInserted(size - 1, newItems.size());
        }
    }

    /**
     * Clears the data held and notifies updates to the RecyclerView
     */
    public void clear() {
        final int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }
}
