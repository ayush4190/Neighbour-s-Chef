package com.neighbourschef.customer.ui.fragment.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.getViewModelErrorMessage
import com.neighbourschef.customer.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class OrdersViewModel(private val uid: String): ViewModel() {
    val orders: Flow<Result<List<Order>, Exception>>
        get() = FirebaseRepository.getOrders(uid)
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class OrdersViewModelFactory(private val uid: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            OrdersViewModel(uid) as T
        } else {
            throw IllegalArgumentException(getViewModelErrorMessage(modelClass, OrdersViewModel::class.java))
        }
}
