package com.example.myapplication.ui.fragment.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentItemDetailBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Product
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_PRODUCT
import com.example.myapplication.util.common.JSON
import com.example.myapplication.util.common.PREFERENCE_CART
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class ItemDetailFragment: BaseFragment<FragmentItemDetailBinding>(), KodeinAware {
    override val kodein by kodein()
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
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFoodInfo()
        setButtonListener()
    }

    private fun initFoodInfo() {
        binding.textFoodId.text = product.id
        binding.textFoodPrice.text = product.price.toString()
        binding.foodDetailCollapsingToolbar.title = product.name
    }

    private fun setButtonListener() {
        binding.buttonFoodAddtocart.setOnClickListener {
            cart += product.copy(quantity = 1)
            (requireActivity() as MainActivity).binding.layoutAppBar.fab.text = getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.stringify(Cart.serializer(), cart))
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Item added")
                .setIcon(android.R.drawable.ic_dialog_info)
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