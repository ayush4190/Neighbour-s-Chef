package com.neighbourschef.customer.ui.fragment.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import com.neighbourschef.customer.databinding.CardAddressBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.ui.fragment.AddressViewHolder
import com.neighbourschef.customer.util.android.base.BaseAdapter

class CheckOutAdapter(items: MutableList<Address>): BaseAdapter<AddressViewHolder, Address>(items, true) {
    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(
            CardAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}
