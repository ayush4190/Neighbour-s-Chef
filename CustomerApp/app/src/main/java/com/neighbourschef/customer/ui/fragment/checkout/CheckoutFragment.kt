package com.neighbourschef.customer.ui.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neighbourschef.customer.databinding.FragmentCheckoutBinding
import com.neighbourschef.customer.util.android.base.BaseFragment

class CheckoutFragment: BaseFragment<FragmentCheckoutBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

//        with(binding.recyclerAddresses) {
//            adapter = this@CheckoutFragment.adapter
//            layoutManager = LinearLayoutManager(context)
//            setHasFixedSize(true)
//        }
    }
}