package com.neighbourschef.vendor.ui.fragment.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class MenuViewModel(private val day: String) : ViewModel() {
    val items: Flow<Result<List<Product>, Exception>>
        get() = FirebaseRepository.getMenu(day)
}

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class MenuViewModelFactory(private val day: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            MenuViewModel(day) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${MenuViewModel::class.java.simpleName}")
        }
}
