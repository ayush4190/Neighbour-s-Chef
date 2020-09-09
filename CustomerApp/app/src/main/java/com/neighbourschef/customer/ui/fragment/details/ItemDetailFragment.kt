package com.neighbourschef.customer.ui.fragment.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentItemDetailBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class ItemDetailFragment: BaseFragment<FragmentItemDetailBinding>(), DIAware {
    override val di by di()
    val sharedPreferences by instance<SharedPreferences>()
    val cart by instance<Cart>()

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
        super.onViewCreated(view, savedInstanceState)

        initFoodInfo()
        setButtonListener()
    }

    private fun initFoodInfo() {
        binding.collapsingToolbar.title = product.name

        binding.textFoodPrice.text = binding.root.context.getString(R.string.set_price, product.price)

        // To be changed eventually
        binding.imgFood.load(R.drawable.food_sample)
        binding.textFoodDescription.text = binding.root.context.getString(R.string.food_description_placeholder)
        binding.imgFoodVegNonVeg.load(R.drawable.green_veg) {
            transformations(CircleCropTransformation())
        }
    }

    private fun setButtonListener() {
        binding.btnAdd.setOnClickListener {
            cart += product.copy(quantity = 1)
            (requireActivity() as MainActivity).binding.layoutAppBar.fab.text = getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.encodeToString(Cart.serializer(), cart))
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Item added")
                .setPositiveButton("Go to cart") { dialog, _ ->
                    dialog.dismiss()
                    findNavController().navigate(MobileNavigationDirections.navigateToCart())
                }
                .setNegativeButton("Continue") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
