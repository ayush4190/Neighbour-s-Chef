package com.example.myapplication.ui.fragment.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.myapplication.databinding.FragmentItemDetailBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Product
import com.example.myapplication.ui.activity.cart.CartActivity
import com.example.myapplication.ui.activity.main.MainActivity
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class ItemDetailFragment: BaseFragment<FragmentItemDetailBinding>(), KodeinAware {
    override val kodein by kodein()
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
        binding.textFoodId.text = product.id.toString()
        binding.textFoodPrice.text = product.price.toString()
        binding.foodDetailCollapsingToolbar.title = product.name
    }

    private fun setButtonListener() {
        binding.buttonFoodAddtocart.setOnClickListener {
            cart += product
            (requireActivity() as MainActivity).binding.layoutAppbar.cartItemNumber.text = cart.size().toString()

            AlertDialog.Builder(activity)
                .setTitle("Successful!")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("Add 1 " + "mItemlist.getProduct_name()" + " to cart!")
                .setPositiveButton("Jump to cart") { _: DialogInterface?, _: Int ->
                    val cartAct = Intent(activity, CartActivity::class.java)
                    startActivity(cartAct)
                }
                .setNegativeButton("Continue", null)
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product): ItemDetailFragment = ItemDetailFragment().apply {
            arguments = bundleOf(
                EXTRA_PRODUCT to product
            )
        }
    }
}