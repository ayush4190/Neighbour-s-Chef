package com.example.myapplication.ui.fragment.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.databinding.CardAddressBinding
import com.example.myapplication.model.Address
import com.example.myapplication.ui.fragment.AddressViewHolder
import com.example.myapplication.util.android.base.BaseAdapter
import com.example.myapplication.util.common.EXTRA_ADDRESS
import com.example.myapplication.util.common.EXTRA_EMAIL

internal class ProfileAdapter(
    items: MutableList<Address>,
    private val navController: NavController
): BaseAdapter<AddressViewHolder, Address>(items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(
            CardAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            setOnClickListener {
                val item = items[adapterPosition]
                navController.navigate(
                    R.id.navigate_to_address_dialog,
                    bundleOf(
                        EXTRA_EMAIL to item.userEmail,
                        EXTRA_ADDRESS to item
                    )
                )
            }
        }
}