package com.example.myapplication.ui.fragment

import com.example.myapplication.databinding.CardAddressBinding
import com.example.myapplication.model.Address
import com.example.myapplication.util.android.base.BaseViewHolder

class AddressViewHolder(binding: CardAddressBinding): BaseViewHolder<CardAddressBinding, Address>(binding) {
    override fun bindTo(item: Address) {
        binding.textAddressName.text = item.addressName
        binding.textAddress.text = item.formattedString()
        binding.textAddressLandmark.text = item.landmark
    }
}