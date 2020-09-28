package com.neighbourschef.vendor.ui.fragment.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.NavController
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.CardOrderBinding
import com.neighbourschef.vendor.model.Order
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.base.BaseAdapter
import com.neighbourschef.vendor.util.android.base.BaseViewHolder
import com.neighbourschef.vendor.util.common.EXTRA_ORDER
import com.neighbourschef.vendor.util.common.EXTRA_UID
import com.neighbourschef.vendor.util.common.humanReadable
import com.neighbourschef.vendor.util.common.toLocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class OrdersAdapter(
    items: MutableList<Pair<String, Order>>,
    private val navController: NavController
) : BaseAdapter<OrdersAdapter.OrderViewHolder, Pair<String, Order>>(items, false) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            CardOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { vh ->
            vh.setOnClickListener {
                val (uid, order) = items[vh.adapterPosition]
                navController.navigate(
                    R.id.navigate_to_order_details,
                    bundleOf(
                        EXTRA_ORDER to order,
                        EXTRA_UID to uid
                    )
                )
            }
        }

    @ExperimentalCoroutinesApi
    class OrderViewHolder(binding: CardOrderBinding) : BaseViewHolder<CardOrderBinding, Pair<String, Order>>(binding) {
        override fun bindTo(item: Pair<String, Order>) {
            binding.btnCancel.isVisible = item.second.status == Order.OrderStatus.PLACED

            binding.textQuantity.text = binding.root.context.getString(
                R.string.set_items,
                item.second.totalQuantity(),
                binding.root.context.resources.getQuantityString(R.plurals.items, item.second.totalQuantity())
            )
            binding.textTotal.text = binding.root.context.getString(
                R.string.set_price,
                String.format("%.2f", item.second.totalPrice())
            )
            binding.textDate.text = item.second.timestamp.toLocalDateTime().humanReadable()
            binding.textStatus.text = binding.root.context.getString(R.string.set_status, item.second.status.toString())

            binding.btnCancel.setOnClickListener {
                item.second.status = Order.OrderStatus.CANCELLED
                FirebaseRepository.saveOrder(item.second, item.first)
            }
        }
    }
}
