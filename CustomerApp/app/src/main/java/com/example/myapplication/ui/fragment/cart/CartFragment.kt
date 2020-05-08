package com.example.myapplication.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.JSON
import com.example.myapplication.util.common.PREFERENCE_CART
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CartFragment: BaseFragment<FragmentCartBinding>(), KodeinAware {
    override val kodein by kodein()
    val sharedPreferences by instance<SharedPreferences>()
    val cart by instance<Cart>()

    private val adapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CartAdapter(cart.products, sharedPreferences, binding.textTotalPrice)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.textTotalPrice.text = getString(R.string.set_price, cart.total())

        with(binding) {
            recyclerCart.apply {
                adapter = this@CartFragment.adapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }

            btnCheckout.setOnClickListener {
                sharedPreferences.edit {
                    putString(PREFERENCE_CART, JSON.stringify(Cart.serializer(), Cart(adapter.items)))
                }
                findNavController().navigate(CartFragmentDirections.navigateToCheckOut())
            }
        }
    }
}