package com.neighbourschef.customer.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogCommentsBinding
import com.neighbourschef.customer.databinding.FragmentCartBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.saveCart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class CartFragment: BaseFragment<FragmentCartBinding>(), DIAware {
    override val di by di()
    val sharedPreferences by instance<SharedPreferences>()
    val cart by instance<Cart>()

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private val uid: String by lazy(LazyThreadSafetyMode.NONE) { auth.currentUser!!.uid
    }
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
        with(binding) {
            layoutCart.isVisible = !cart.isEmpty()
            textEmptyState.isVisible = cart.isEmpty()

            textTotalPrice.text = getString(R.string.set_price, String.format("%.2f", cart.total()))
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
                    .setPositiveButton("Place order") { _, _ ->
                        val order = Order.fromCart(getCart(sharedPreferences))
                        order.comments = dialogBinding.editComments.asString()
                        FirebaseRepository.saveOrder(order, uid)
                        saveCart(sharedPreferences, Cart())

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Order placed")
                            .setMessage("Order ID: ${order.id}\nOrder Status: ${order.status}")
                            .setNeutralButton("OK") { d, _ -> d.dismiss() }
                            .show()

                        navController.navigate(
                            MobileNavigationDirections.navigateToHome()
                        )
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                navController.navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
