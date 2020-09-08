package com.neighbourschef.customer.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogCommentsBinding
import com.neighbourschef.customer.databinding.FragmentCartBinding
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.saveCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class CartFragment: BaseFragment<FragmentCartBinding>(), DIAware {
    override val di by di()
    val sharedPreferences by instance<SharedPreferences>()
    val cart by instance<Cart>()
    val database by instance<CustomerDatabase>()

    private val adapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CartAdapter(cart.products, sharedPreferences, binding.textTotalPrice)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentCartBinding.inflate(inflater, container, false)
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
                val dialogBinding = DialogCommentsBinding.inflate(LayoutInflater.from(requireContext()))
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Confirm order")
                    .setView(dialogBinding.root)
                    .setMessage("Add comments")
                    .setPositiveButton("Place order") { dialog, _ ->
                        val order = Order.fromCart(getCart(sharedPreferences))
                        order.comments = dialogBinding.editComments.asString()
                        lifecycleScope.launch(Dispatchers.IO) {
                            database.orderDao().insert(order)
                        }
                        saveCart(sharedPreferences, Cart.EMPTY)
                        dialog.dismiss()

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Order placed")
                            .setMessage("Order ID: ${order.id}\nOrder Status: ${order.status}")
                            .setNeutralButton("OK") { d, _ -> d.dismiss() }
                            .show()

                        findNavController().navigate(
                            MobileNavigationDirections.navigateToHome(),
                            navOptions {
                                popUpTo(R.id.nav_cart) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()}
                    .show()
            }
        }
    }
}