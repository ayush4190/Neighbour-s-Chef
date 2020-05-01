package com.example.myapplication.ui.fragment.menu.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.util.android.FirebaseRepository
import com.example.myapplication.util.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TodayMenuViewModel: ViewModel() {
    private val _products: LiveData<State> = FirebaseRepository.getTodaysMenu()
        .asLiveData(viewModelScope.coroutineContext)

    val products: LiveData<State>
        get() = _products
}