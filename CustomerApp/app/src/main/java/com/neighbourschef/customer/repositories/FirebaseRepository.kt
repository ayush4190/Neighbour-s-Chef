package com.neighbourschef.customer.repositories

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.util.android.listenMenu
import com.neighbourschef.customer.util.android.listenOrder
import com.neighbourschef.customer.util.common.PATH_MENU
import com.neighbourschef.customer.util.common.PATH_ORDERS
import com.neighbourschef.customer.util.common.PATH_ROOT
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Singleton repository that handles all Firebase Realtime Database calls
 * Explicitly meant for Repository layer in MVVM
 */
@ExperimentalCoroutinesApi
object FirebaseRepository {
    // Lazy instantiation so that the field is initialized only when needed
    private val databaseReference: DatabaseReference by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.database.reference.child(PATH_ROOT)
    }

    fun getMenu(day: String): Flow<UiState> = databaseReference.child(PATH_MENU).child(day).listenMenu()

    fun getOrders(email: String): Flow<UiState> = databaseReference.child(PATH_ORDERS).child(email).listenOrder()
}
