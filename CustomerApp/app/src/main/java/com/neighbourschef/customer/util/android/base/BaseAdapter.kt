package com.neighbourschef.customer.util.android.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base class for RecyclerView adapters
 * @param <VH> type of RecyclerView.ViewHolder (must extend BaseViewHolder)
 * @param <T> type of item held by the adapter
 */
abstract class BaseAdapter<VH: BaseViewHolder<out ViewBinding, T>, T>(
    val items: MutableList<T>,
    stableIds: Boolean
): RecyclerView.Adapter<VH>() {
    init {
        super.setHasStableIds(stableIds)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bindTo(items[position])

    override fun getItemCount(): Int = items.size

    /**
     * Submit a new list of items to be added to the adapter
     * @param newItems list of new items
     * @param overwrite if the existing items must be overwritten or not. If true, items are
     * replaced, else they are added
     *
     * @see BaseAdapter.addItem
     */
    fun submitList(newItems: List<T>, overwrite: Boolean = true) {
        val size = items.size
        if (overwrite) {
            clear()
            items += newItems
            notifyItemRangeInserted(0, newItems.size)
        } else {
            items += newItems
            notifyItemRangeInserted(size - 1, newItems.size)
        }
    }

    /**
     * Add a single item to the adapter (better while adding single item instead of wrapping in a list)
     * @param item item submitted
     *
     * @see BaseAdapter.submitList
     */
    fun addItem(item: T) {
        items += item
        notifyItemInserted(items.size - 1)
    }

    /**
     * Clears the data held and notifies updates to the RecyclerView
     */
    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}
