package com.neighbourschef.customer.ui.fragment.menu.tomorrow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TomorrowMenuViewModel: ViewModel() {
    private val savedProducts: LiveData<State> = FirebaseRepository.getTomorrowsMenu()
        .asLiveData(viewModelScope.coroutineContext)

    val products: LiveData<State>
        get() = savedProducts
}
