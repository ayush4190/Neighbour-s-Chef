package com.neighbourschef.customer.ui.fragment.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class ProfileViewModel(private val uid: String) : ViewModel() {
    val user: Flow<Result<User, Exception>>
        get() = FirebaseRepository.getUser(uid)

    fun saveUser(user: User) {
        FirebaseRepository.saveUser(user, uid)
    }
}

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class ProfileViewModelFactory(private val uid: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(uid) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} is not assignable by ${ProfileViewModel::class.java.simpleName}")
        }
}
