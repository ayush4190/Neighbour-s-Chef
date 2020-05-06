package com.example.myapplication.ui.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCartBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.ui.fragment.checkout.CheckOutFragment
import com.example.myapplication.util.android.CartSwipeCallback
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.log
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CartFragment: BaseFragment<FragmentCartBinding>(), KodeinAware {
    override val kodein by kodein()
    val cart by instance<Cart>()

    private val adapter: CartAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CartAdapter(cart.products).also {
            it.setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cart.log()
        setupViews()
    }

    private fun setupViews() {
        binding.textTotalPrice.text = getString(R.string.set_price, cart.total())

        initSwipe()
        with(binding) {
            recyclerCart.apply {
                adapter = this@CartFragment.adapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }

            btnBack.setOnClickListener { requireActivity().finish() }

            btnCheckout.setOnClickListener {
                requireActivity().supportFragmentManager
                    .commit {
                        setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.fade_out
                        )
                        replace(R.id.main_fragment_container, CheckOutFragment.newInstance(cart))
                        addToBackStack(CartFragment::class.java.name)
                    }
            }
        }
    }

    private fun initSwipe() {
        val itemTouchHelper = ItemTouchHelper(CartSwipeCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerCart)
    }

    companion object {
        @JvmStatic fun newInstance(): CartFragment = CartFragment()
    }
}