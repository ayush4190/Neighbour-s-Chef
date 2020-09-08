package com.neighbourschef.customer.ui.fragment.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardOrderBinding
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.base.BaseViewHolder
import com.neighbourschef.customer.util.android.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter

class HistoryAdapter(
    items: MutableList<Order>,
    private val database: CustomerDatabase
): BaseAdapter<HistoryAdapter.HistoryViewHolder, Order>(items, true) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            CardOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            database
        ).also {
            it.setOnClickListener { v ->
                toast(v.context, "Display items")
            }
        }

    inner class HistoryViewHolder(
        binding: CardOrderBinding,
        private val database: CustomerDatabase
    ): BaseViewHolder<CardOrderBinding, Order>(binding) {
        override fun bindTo(item: Order) {
            binding.historyCancel.isVisible = item.status != Order.OrderStatus.CANCELLED

            binding.textId.text = item.id.toString()
            binding.textDate.text = item.timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            binding.textQuantity.text = item.totalQuantity().toString()
            binding.textTotal.text = binding.root.context.getString(R.string.set_price, item.totalPrice())

            binding.historyCancel.setOnClickListener {
                item.status = Order.OrderStatus.CANCELLED
                CoroutineScope(Dispatchers.IO).launch {
                    database.orderDao().update(item)
                }
            }
        }
    }
}