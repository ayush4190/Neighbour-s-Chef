package com.example.myapplication.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogCommentsBinding
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Order
import com.example.myapplication.util.android.asString
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.getCart
import com.example.myapplication.util.android.saveCart
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CartFragment: BaseFragment<FragmentCartBinding>(), KodeinAware {
    override val kodein by kodein()
    val sharedPreferences by instance<SharedPreferences>()
    val cart by instance<Cart>()
    val database by instance<CustomerDatabase>()
    private val firebaseAuth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) {
        FirebaseAuth.getInstance()
    }

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
                            update_order(order)
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

    private fun update_order(order: Order)
    {
        val currentUser = firebaseAuth.currentUser



       lateinit var database: DatabaseReference
        database = Firebase.database.reference.child("ORDER").child(currentUser.toString())
        database.setValue(order)
    }
}