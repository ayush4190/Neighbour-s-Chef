package com.neighbourschef.customer.ui.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentOrdersBinding
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.Result
import com.neighbourschef.customer.util.common.VEILED_ITEM_COUNT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OrdersFragment: BaseFragment<FragmentOrdersBinding>() {
    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    private val viewModel by viewModels<OrdersViewModel> { OrdersViewModelFactory(auth.currentUser!!.uid) }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        OrdersAdapter(mutableListOf(), auth.currentUser!!.uid, navController)
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
        currentBinding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerOrders.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(requireContext()))
            addVeiledItems(VEILED_ITEM_COUNT)
        }

        lifecycleScope.launch {
            viewModel.orders.collectLatest {
                binding.recyclerOrders.veil()
                binding.recyclerOrders.isVisible = it is Result.Value

                when (it) {
                    is Result.Value -> {
                        binding.recyclerOrders.unVeil()
                        adapter.submitList(it.value.sortedByDescending { o -> o.timestamp })

                        binding.textEmptyState.isVisible = adapter.itemCount == 0
                        binding.recyclerOrders.isVisible = adapter.itemCount != 0
                    }
                    is Result.Error -> {
                        binding.textEmptyState.isVisible = true
                        toast(it.error.message ?: it.error.toString())
                    }
                }
            }
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
            transformations(
                CircleCropTransformation(),
                CircleBorderTransformation(
                    borderColor = ContextCompat.getColor(requireContext(), R.color.colorOnPrimary)
                )
            )
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
