package com.neighbourschef.customer.ui.fragment.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentItemDetailBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import org.koin.android.ext.android.inject

class ItemDetailFragment: BaseFragment<FragmentItemDetailBinding>() {
    private val sharedPreferences: SharedPreferences by inject()
    private val cart: Cart by lazy(LazyThreadSafetyMode.NONE) { getCart(sharedPreferences) }

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    private val product: Product by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_PRODUCT] as Product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textFoodPrice.text = requireContext().getString(
            R.string.set_price,
            String.format("%.2f", product.price)
        )

        binding.imgFood.load(product.firebaseUrl) {
            placeholder(R.drawable.ic_food_default_64)
            fallback(R.drawable.ic_food_default_64)
        }
        binding.textFoodDescription.text = product.description
        binding.textForDay.text = requireContext().getString(R.string.for_day, product.day)

        val drawable = if (product.veg) R.drawable.green_veg else R.drawable.red_non_veg
        binding.imgFoodVegNonVeg.load(drawable) {
            transformations(CircleCropTransformation())
        }

        binding.btnAdd.setOnClickListener {
            cart += product.copy(quantity = 1)
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.encodeToString(Cart.serializer(), cart))
            }
            toast("Added to cart")
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
