package com.neighbourschef.vendor.ui.fragment.order

import androidx.lifecycle.ViewModel
import com.neighbourschef.vendor.model.Order
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class OrdersViewModel : ViewModel() {
    val orders: Flow<Result<List<Pair<String, Order>>, Exception>>
        get() = FirebaseRepository.getOrders()
}
