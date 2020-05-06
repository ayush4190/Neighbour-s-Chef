package com.example.myapplication.ui.fragment.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentItemDetailBinding
import com.example.myapplication.model.Product
import com.example.myapplication.ui.activity.cart.CartActivity
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_PRODUCT

class ItemDetailFragment :
    BaseFragment<FragmentItemDetailBinding?>() {
    private var product: Product? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentItemDetailBinding.inflate(
            inflater,
            container,
            false
        )

            return binding!!.root


    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        product = requireArguments().getParcelable(EXTRA_PRODUCT)
        initFoodInfo()
        setButtonListener()
    }

    private fun initFoodInfo() {
        binding!!.textFoodId.text = product!!.id.toString()
        binding!!.textFoodPrice.text = product!!.price.toString()
        binding!!.foodDetailCollapsingToolbar.title = product!!.name
    }

    private fun setButtonListener() {
        binding!!.buttonFoodAddtocart.setOnClickListener { view: View? ->

            //  ShoppingCartItem.getInstance(getContext()).addToCart(food);
            val cartNumber =
                requireActivity().findViewById<TextView>(R.id.cart_item_number)
            //   cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getSize()));
            AlertDialog.Builder(activity).setTitle("Successful!").setIcon(
                android.R.drawable.ic_dialog_info
            )
                .setMessage("Add 1 " + "mItemlist.getProduct_name()" + " to cart!")
                .setPositiveButton(
                    "Jump to cart"
                ) { dialogInterface: DialogInterface?, i: Int ->
                    val cartAct = Intent(activity, CartActivity::class.java)
                    startActivity(cartAct)
                }
                .setNegativeButton("Continue", null).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(product: Product?): ItemDetailFragment {
            val fragment = ItemDetailFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }
}