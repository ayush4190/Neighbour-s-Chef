package com.neighbourschef.customer.ui.fragment.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MenuViewModel(day: String) : ViewModel() {
    private val savedItems: LiveData<State> = FirebaseRepository.getMenu(day)
        .asLiveData(viewModelScope.coroutineContext)
    
    val items: LiveData<State>
        get() = savedItems
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
