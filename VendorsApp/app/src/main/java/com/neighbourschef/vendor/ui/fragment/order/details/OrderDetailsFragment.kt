package com.neighbourschef.vendor.ui.fragment.order.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentOrderDetailsBinding
import com.neighbourschef.vendor.model.Order
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.EXTRA_ORDER
import com.neighbourschef.vendor.util.common.EXTRA_UID
import com.neighbourschef.vendor.util.common.Result
import com.neighbourschef.vendor.util.common.humanReadable
import com.neighbourschef.vendor.util.common.toLocalDateTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {
    private val uid: String by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getString(EXTRA_UID)!!
    }
    private val order: Order by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getParcelable(EXTRA_ORDER)!!
    }

    private val adapter: OrderDetailsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        OrderDetailsAdapter(order.products.toMutableList())
    }
    private val viewModel: OrderDetailsViewModel by viewModels { OrderDetailsViewModelFactory(uid) }
    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            textId.text = order.id
            textQuantity.text = requireContext().getString(
                R.string.set_items,
                order.totalQuantity(),
                requireContext().resources.getQuantityString(R.plurals.items, order.totalQuantity())
            )
            textTotal.text = requireContext().getString(R.string.set_price, String.format("%.2f", order.totalPrice()))
            textDate.text = order.timestamp.toLocalDateTime().humanReadable()
            textComments.text = order.comments
            textStatus.text = requireContext().getString(R.string.set_status, order.status)

            recyclerItems.adapter = adapter
            recyclerItems.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recyclerItems.setHasFixedSize(true)
        }

        lifecycleScope.launch {
            viewModel.user.collectLatest {
                binding.veilUser.veil()
                when (it) {
                    is Result.Value -> {
                        with(binding) {
                            veilUser.unVeil()
                            textUserName.text = requireContext().getString(R.string.set_value, "Name", it.value.name)
                            textUserEmail.text = requireContext().getString(R.string.set_value, "Email", it.value.email)
                            textUserPhone.text = requireContext().getString(R.string.set_value, "Phone", it.value.phoneNumber)
                            textAddress.text = it.value.address.formattedString()
                            textAddressLandmark.text = it.value.address.landmark
                        }
                    }
                    is Result.Error -> {
                        toast { it.error.message ?: it.error.toString() }
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_orders, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_update_order -> {
            if (order.status == Order.OrderStatus.PLACED) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.title_update_order_status)
                    .setSingleChoiceItems(
                        arrayOf("Cancel", "Complete"),
                        0
                    ) { dialog, which ->
                        val status = if (which == 0) Order.OrderStatus.CANCELLED else Order.OrderStatus.COMPLETED
                        order.status = status
                        FirebaseRepository.saveOrder(order, uid)
                        dialog.dismiss()
                        findNavController().navigateUp()
                    }
                    .show()
            } else {
                toast { "The order is already ${order.status}" }
            }
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
