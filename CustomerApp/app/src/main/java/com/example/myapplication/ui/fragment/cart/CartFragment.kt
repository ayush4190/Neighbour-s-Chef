package com.example.myapplication.ui.fragment.cart

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.ui.fragment.checkout.CheckOutFragment
import com.example.myapplication.util.android.CartSwipeCallback
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_PRODUCT
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class CartFragment private constructor(): BaseFragment<FragmentCartBinding>(), KodeinAware {
    override val kodein by closestKodein()
    override val kodeinContext = kcontext(requireContext())
    private val sharedPreferences by kodein.instance<SharedPreferences>()
    private val cart by kodein.instance<Cart>()

    private var adapter: CartAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        Toast.makeText(requireContext(), sharedPreferences.getString(EXTRA_PRODUCT, "Empty"), Toast.LENGTH_LONG).show()
    }

    private fun init() {
//         binding.textTotalPrice.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getPrice()));
        adapter = CartAdapter()
        initSwipe()
        binding!!.recyclerCart.adapter = adapter
        binding!!.recyclerCart.setHasFixedSize(false)
        binding!!.recyclerCart.layoutManager = LinearLayoutManager(context)
        binding!!.btnBack.setOnClickListener { requireActivity().finish() }
        binding!!.btnCheckout.setOnClickListener {
            val checkOutFragment = CheckOutFragment.newInstance(null)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .replace(R.id.main_fragment_container, checkOutFragment)
                .addToBackStack(CartFragment::class.java.name)
                .commit()
        }
    }

    private fun initSwipe() {
        val itemTouchHelper = ItemTouchHelper(CartSwipeCallback(adapter!!))
        itemTouchHelper.attachToRecyclerView(binding!!.recyclerCart)
    }

    companion object {
        @JvmStatic fun newInstance(): CartFragment = CartFragment()
    }
}