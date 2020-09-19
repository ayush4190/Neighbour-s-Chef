package com.neighbourschef.customer.ui.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neighbourschef.customer.databinding.DialogOrderItemsBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.common.EXTRA_ITEMS

class OrderItemsDialogFragment : DialogFragment() {
    private var currentBinding: DialogOrderItemsBinding? = null
    private val binding: DialogOrderItemsBinding
        get() = currentBinding!!

    private val items: ArrayList<Product> by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getParcelableArrayList(EXTRA_ITEMS)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = DialogOrderItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerItems.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = FoodAdapter(items.toMutableList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}
