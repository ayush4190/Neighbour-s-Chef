package com.neighbourschef.vendor.ui.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentItemDetailsBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.asString
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.closeKeyboard
import com.neighbourschef.vendor.util.android.snackbar
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ItemDetailsFragment : BaseFragment<FragmentItemDetailsBinding>() {
    private val product: Product by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getParcelable(EXTRA_PRODUCT)!!
    }
    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgFood.load(product.firebaseUrl) {
            placeholder(R.drawable.ic_food_default_64)
            fallback(R.drawable.ic_food_default_64)
        }
        binding.editName.setText(product.name)
        binding.editDescription.setText(product.description)
        binding.editPrice.setText(String.format("%.2f", product.price))
        binding.textForDay.text = requireContext().getString(R.string.for_day, product.day)

        val drawable = if (product.veg) R.drawable.green_veg else R.drawable.red_non_veg
        binding.imgFoodVegNonVeg.load(drawable) {
            transformations(CircleCropTransformation())
        }

        binding.fab.setOnClickListener {
            closeKeyboard()
            FirebaseRepository.saveItem(
                product.copy(
                    name = binding.editName.asString().trim(),
                    description = binding.editDescription.asString().trim(),
                    price = binding.editPrice.asString().toDouble()
                )
            )
            navController.navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_item_details, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_delete -> {
            snackbar("This item will be deleted forever!", "Delete") {
                navController.navigateUp()
                FirebaseRepository.deleteItem(product)
                toast { "Item deleted!" }
            }
            true
        }
        R.id.action_help -> {
            navController.navigate(MobileNavigationDirections.navigateToHelp())
            true
        }
        R.id.action_logout -> {
            auth.signOut()
            navController.navigate(MobileNavigationDirections.navigateToLogin())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
