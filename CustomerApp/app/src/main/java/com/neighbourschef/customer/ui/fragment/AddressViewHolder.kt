package com.neighbourschef.customer.ui.fragment

import com.neighbourschef.customer.databinding.CardAddressBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.util.android.base.BaseViewHolder

class AddressViewHolder(binding: CardAddressBinding): BaseViewHolder<CardAddressBinding, Address>(binding) {
    override fun bindTo(item: Address) {
        binding.textAddressName.text = item.addressName
        binding.textAddress.text = item.formattedString()
        binding.textAddressLandmark.text = item.landmark
    }
}