package com.example.myapplication.ui.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.util.android.CartSwipeCallback
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.log
import com.example.myapplication.util.common.EXTRA_CART
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CartFragment: BaseFragment<FragmentCartBinding>(), KodeinAware {
    override val kodein by kodein()
    val cart by instance<Cart>()

    private val adapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) { CartAdapter(cart.products) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cart.log()
        setupViews()
    }

    private fun setupViews() {
        binding.textTotalPrice.text = getString(R.string.set_price, cart.total())

        initSwipe()
        with(binding) {
            recyclerCart.apply {
                adapter = this@CartFragment.adapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }

            btnCheckout.setOnClickListener {
                findNavController().navigate(
                    R.id.navigate_to_check_out,
                    bundleOf(EXTRA_CART to cart)
                )
            }
        }
    }

    private fun initSwipe() {
        val itemTouchHelper = ItemTouchHelper(CartSwipeCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerCart)
    }

    companion object {
        @JvmStatic fun newInstance(): CartFragment = CartFragment()
    }
}