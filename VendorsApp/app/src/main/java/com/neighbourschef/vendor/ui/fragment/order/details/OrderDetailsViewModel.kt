package com.neighbourschef.vendor.ui.fragment.order.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neighbourschef.vendor.model.User
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class OrderDetailsViewModel(private val uid: String) : ViewModel() {
    val user: Flow<Result<User, Exception>>
        get() = FirebaseRepository.getUser(uid)
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class OrderDetailsViewModelFactory(private val uid: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(OrderDetailsViewModel::class.java)) {
            OrderDetailsViewModel(uid) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable from ${OrderDetailsViewModel::class.java.simpleName}")
        }
}
