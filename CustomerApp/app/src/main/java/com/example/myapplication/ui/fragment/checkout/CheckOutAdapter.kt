package com.example.myapplication.ui.fragment.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapplication.databinding.CardAddressBinding
import com.example.myapplication.model.Address
import com.example.myapplication.ui.fragment.AddressViewHolder
import com.example.myapplication.util.android.base.BaseAdapter

class CheckOutAdapter(items: MutableList<Address>): BaseAdapter<AddressViewHolder, Address>(items) {
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