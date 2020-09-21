package com.neighbourschef.customer.ui.fragment.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.android.saveCart
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import org.koin.android.ext.android.inject

class ItemDetailFragment: BaseFragment<FragmentItemDetailBinding>() {
    private val sharedPreferences: SharedPreferences by inject()
    private val cart: Cart by lazy(LazyThreadSafetyMode.NONE) { getCart(sharedPreferences, auth.uid) }

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    private val product: Product by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_PRODUCT] as Product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            saveCart(sharedPreferences, auth.uid!!, cart)
            toast("Added to cart")
            navController.navigateUp()
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
            transformations(CircleCropTransformation(), CircleBorderTransformation())
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
