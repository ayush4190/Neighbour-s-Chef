package com.neighbourschef.customer.ui.fragment.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Order

class OrdersViewModel(database: CustomerDatabase): ViewModel() {
    private val savedOrders: LiveData<List<Order>> = database.orderDao()
        .getAllOrdersDistinctUntilChanged()
        .asLiveData(viewModelScope.coroutineContext)

    val orders: LiveData<List<Order>>
        get() = savedOrders
}

@Suppress("UNCHECKED_CAST")
class OrdersViewModelFactory(private val database: CustomerDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            OrdersViewModel(database) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${OrdersViewModel::class.java.simpleName}")
        }
}
