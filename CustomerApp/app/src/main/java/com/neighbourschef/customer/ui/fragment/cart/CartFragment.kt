package com.neighbourschef.customer.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
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
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.saveCart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class CartFragment: BaseFragment<FragmentCartBinding>() {
    private val sharedPreferences: SharedPreferences by inject()
    private val cart by lazy(LazyThreadSafetyMode.NONE) { getCart(sharedPreferences, uid) }

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private val uid: String by lazy(LazyThreadSafetyMode.NONE) { auth.uid!! }
    private val adapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CartAdapter(cart.products, uid, sharedPreferences, binding.textTotalPrice)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                        val comments = dialogBinding.editComments.asString().trim()
                        val orders = Order.fromProducts(getCart(sharedPreferences, uid).products)
                        if (orders.size > 1) Toast.makeText(
                            requireContext(),
                            "Split into 2 orders as items differ by day",
                            Toast.LENGTH_LONG
                        ).show()

                        orders.forEach {
                            it.comments = comments
                            FirebaseRepository.saveOrder(it, uid)
                        }
                        saveCart(sharedPreferences, uid, Cart())

                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Order placed")
                            .setItems(orders.map {
                                "Order ID: ${it.id}\nOrder Status: ${it.status}"
                            }.toTypedArray()) { _, _ -> }
                            .setNeutralButton("OK") { _, _ ->  }
                            .show()

                        navController.navigate(
                            MobileNavigationDirections.navigateToMenu()
                        )
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_profile)!!
        val imageView = menuItem.actionView?.findViewById<ImageView>(R.id.img_user_account)!!
        menuItem.actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        imageView.load(auth.currentUser?.photoUrl) {
            transformations(
                CircleCropTransformation(),
                CircleBorderTransformation(
                    borderColor = ContextCompat.getColor(requireContext(), R.color.colorOnPrimary)
                )
            )
            placeholder(R.drawable.ic_person_outline_24)
            fallback(R.drawable.ic_person_outline_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_profile -> {
                navController.navigate(MobileNavigationDirections.navigateToProfile())
                true
            }
            R.id.action_help -> {
                navController.navigate(MobileNavigationDirections.navigateToHelp())
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                (requireActivity() as MainActivity).googleSignInClient.signOut()
                navController.navigate(MobileNavigationDirections.navigateToRegistration())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
