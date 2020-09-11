package com.neighbourschef.customer.ui.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardOrderBinding
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.base.BaseViewHolder
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.toLocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.threeten.bp.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class OrdersAdapter(
    items: MutableList<Order>,
    private val uid: String
): BaseAdapter<OrdersAdapter.OrdersViewHolder, Order>(items, true) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder =
        OrdersViewHolder(
            CardOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            uid
        ).also {
            it.setOnClickListener { v ->
                v.toast("Display items")
            }
        }

    @ExperimentalCoroutinesApi
    inner class OrdersViewHolder(
        binding: CardOrderBinding,
        private val uid: String
    ): BaseViewHolder<CardOrderBinding, Order>(binding) {
        override fun bindTo(item: Order) {
            binding.btnCancel.isVisible = item.status != Order.OrderStatus.CANCELLED

            binding.textId.text = item.id.toString()
            binding.textDate.text = item.timestamp.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            binding.textQuantity.text = item.totalQuantity().toString()
            binding.textTotal.text = binding.root.context.getString(R.string.set_price, item.totalPrice())

            binding.btnCancel.setOnClickListener {
                item.status = Order.OrderStatus.CANCELLED
                FirebaseRepository.saveOrder(item, uid)
            }
        }
    }
}
