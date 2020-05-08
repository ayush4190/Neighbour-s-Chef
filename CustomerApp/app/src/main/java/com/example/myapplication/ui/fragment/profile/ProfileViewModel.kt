package com.example.myapplication.ui.fragment.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.CustomerApp
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.UserWithAddress
import kotlinx.coroutines.flow.Flow
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileViewModel(context: Context): ViewModel(), KodeinAware {
    override val kodein = (context.applicationContext as CustomerApp).kodein
    val database by instance<CustomerDatabase>()

    private val _userWithAddress: Flow<UserWithAddress?> = database.addressDao().loadAddresses()

    val userWithAddress: Flow<UserWithAddress?>
        get() = _userWithAddress
}

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(context) as T
        } else {
            throw IllegalArgumentException("${modelClass.simpleName} cannot be instantiated")
        }
}