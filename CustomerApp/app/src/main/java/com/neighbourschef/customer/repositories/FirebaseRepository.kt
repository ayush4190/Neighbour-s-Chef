package com.neighbourschef.customer.repositories

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.android.listenMenu
import com.neighbourschef.customer.util.android.listenOrder
import com.neighbourschef.customer.util.android.listenUser
import com.neighbourschef.customer.util.common.PATH_MENU
import com.neighbourschef.customer.util.common.PATH_ORDERS
import com.neighbourschef.customer.util.common.PATH_USERS
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Singleton repository that handles all Firebase Realtime Database calls
 * Explicitly meant for Repository layer in MVVM
 */
@ExperimentalCoroutinesApi
object FirebaseRepository {
    private val databaseReference: DatabaseReference by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.database.reference
    }

    /**
     * Retrieve menu stored in Firebase by [day]
     *
     * @return [Flow] with menu items wrapped in [UiState]
     */
    fun getMenu(day: String): Flow<UiState> = databaseReference.child(PATH_MENU).child(day).listenMenu()

    fun getOrders(uid: String): Flow<UiState> = databaseReference.child(PATH_ORDERS).child(uid).listenOrder()

    fun saveOrder(order: Order, uid: String) {
        databaseReference.child(PATH_ORDERS).child(uid).child(order.id.toString()).setValue(order)
    }

    fun getUser(uid: String): Flow<UiState> = databaseReference.child(PATH_USERS).child(uid).listenUser()

    fun saveUser(user: User, uid: String) {
        databaseReference.child(PATH_USERS).child(uid).setValue(user)
    }
}
