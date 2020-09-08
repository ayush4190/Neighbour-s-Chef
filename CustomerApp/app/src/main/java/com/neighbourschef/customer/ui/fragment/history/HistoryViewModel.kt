package com.neighbourschef.customer.ui.fragment.history

import androidx.lifecycle.*
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Order

class HistoryViewModel(database: CustomerDatabase): ViewModel() {
    private val savedOrders: LiveData<List<Order>> = database.orderDao()
        .getAllOrdersDistinctUntilChanged()
        .asLiveData(viewModelScope.coroutineContext)

    val orders: LiveData<List<Order>>
        get() = savedOrders
}

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory(private val database: CustomerDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            HistoryViewModel(database) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${HistoryViewModel::class.java.simpleName}")
        }
}