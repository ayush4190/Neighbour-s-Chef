package com.neighbourschef.customer.ui.fragment.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import coil.load
import coil.transform.CircleCropTransformation
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentItemDetailBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.common.EXTRA_DAY
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
    private val day: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_DAY] as String
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

        binding.collapsingToolbar.title = product.name

        binding.textFoodPrice.text = binding.root.context.getString(R.string.set_price, product.price)

        // To be changed eventually
        binding.imgFood.load(R.drawable.food_sample)
        binding.textFoodDescription.text = binding.root.context.getString(R.string.food_description_placeholder)
        binding.textForDate.text = requireContext().getString(R.string.for_date, day)

        val drawable = if (product.veg) R.drawable.green_veg else R.drawable.red_non_veg
        binding.imgFoodVegNonVeg.load(drawable) {
            transformations(CircleCropTransformation())
        }

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
        }
    }
}