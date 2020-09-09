package com.neighbourschef.vendor.ui.fragment.home

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.OrderViewBinding
import com.neighbourschef.vendor.model.OrderDetail
import com.neighbourschef.vendor.ui.activity.order.OrderActivity
import com.neighbourschef.vendor.ui.fragment.order.OrderListFragment
import com.neighbourschef.vendor.util.android.base.BaseViewHolder

class OrderViewHolder(binding: OrderViewBinding) :
    BaseViewHolder<OrderViewBinding, OrderDetail>(binding) {
    private val context: Context? = null

    override fun bindTo(item: OrderDetail) {
        binding.orderView.setOnClickListener {
            val activity = context as OrderActivity?
            val floatingActionButton = activity!!.findViewById<FloatingActionButton>(R.id.fab)
            floatingActionButton.visibility = View.GONE
            val myFragment: Fragment = OrderListFragment.newInstance()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, myFragment)
                .addToBackStack("Complete_order_list").commit()
        }
    }
}
