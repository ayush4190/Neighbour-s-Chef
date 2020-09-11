package com.neighbourschef.customer.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProfileViewModel(private val userRef: String) : ViewModel() {
    private val savedUser: LiveData<UiState> = FirebaseRepository.getUser(userRef)
        .asLiveData(viewModelScope.coroutineContext)

    val user: LiveData<UiState>
        get() = savedUser

    fun saveUser(user: User) {
        viewModelScope.launch {
            FirebaseRepository.saveUser(user, userRef)
        }
    }
}

@ExperimentalCoroutinesApi
class ProfileViewModelFactory(private val ref: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(ref) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${ProfileViewModel::class.java.simpleName}")
        }
}
