package com.example.myapplication.ui.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCheckoutBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_CART

class CheckOutFragment private constructor(): BaseFragment<FragmentCheckoutBinding>() {
    private val cart: Cart by lazy(LazyThreadSafetyMode.NONE) { requireArguments()[EXTRA_CART] as Cart }
    private val adapter: CheckOutAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CheckOutAdapter(cart.products).also {
            it.setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(binding!!.recyclerCheckout) {
            adapter = this@CheckOutFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    companion object {
        fun newInstance(cart: Cart): CheckOutFragment = CheckOutFragment().apply {
            arguments = bundleOf(
                EXTRA_CART to cart
            )
        }
    }
}