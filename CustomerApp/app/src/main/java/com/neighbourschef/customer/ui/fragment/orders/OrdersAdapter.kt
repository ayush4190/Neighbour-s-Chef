package com.neighbourschef.customer.ui.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.CardOrderBinding
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.base.BaseAdapter
import com.neighbourschef.customer.util.android.base.BaseViewHolder
import com.neighbourschef.customer.util.common.EXTRA_ITEMS
import com.neighbourschef.customer.util.common.humanReadable
import com.neighbourschef.customer.util.common.toLocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class OrdersAdapter(
    items: MutableList<Order>,
    private val uid: String,
    private val navController: NavController
): BaseAdapter<OrdersAdapter.OrdersViewHolder, Order>(items, true) {
    override fun getItemId(position: Int): Long = items[position].id.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder =
        OrdersViewHolder(
            CardOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            uid
        ).also {
            it.setOnClickListener { _ ->
                navController.navigate(
                    R.id.navigate_to_items_dialog,
                    bundleOf(EXTRA_ITEMS to items[it.adapterPosition].products)
                )
            }
        }

    @ExperimentalCoroutinesApi
    inner class OrdersViewHolder(
        binding: CardOrderBinding,
        private val uid: String
    ): BaseViewHolder<CardOrderBinding, Order>(binding) {
        override fun bindTo(item: Order) {
            binding.btnCancel.isVisible = item.status != Order.OrderStatus.CANCELLED

            binding.textId.text = binding.root.context.getString(R.string.order, item.id)
            binding.textQuantity.text = binding.root.context.getString(
                R.string.set_items,
                item.totalQuantity(),
                binding.root.context.resources.getQuantityString(R.plurals.items, item.totalQuantity())
            )
            binding.textTotal.text = binding.root.context.getString(R.string.set_price, String.format("%.2f", item.totalPrice()))
            binding.textDate.text = item.timestamp.toLocalDateTime().humanReadable()
            binding.textStatus.text = binding.root.context.getString(R.string.set_status, item.status.toString())

            binding.btnCancel.setOnClickListener {
                item.status = Order.OrderStatus.CANCELLED
                FirebaseRepository.saveOrder(item, uid)
            }
        }
    }
}
