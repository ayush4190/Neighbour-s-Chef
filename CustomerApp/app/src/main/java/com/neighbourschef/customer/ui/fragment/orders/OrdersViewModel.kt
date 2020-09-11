package com.neighbourschef.customer.ui.fragment.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class OrdersViewModel(uid: String): ViewModel() {
    private val savedOrders: LiveData<UiState> = FirebaseRepository.getOrders(uid)
        .asLiveData(viewModelScope.coroutineContext)

    val orders: LiveData<UiState>
        get() = savedOrders
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class OrdersViewModelFactory(private val uid: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(OrdersViewModel::class.java)) {
            OrdersViewModel(uid) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${OrdersViewModel::class.java.simpleName}")
        }
}
