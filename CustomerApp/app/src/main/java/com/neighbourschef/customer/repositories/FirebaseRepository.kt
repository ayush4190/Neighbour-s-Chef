package com.neighbourschef.customer.repositories

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.common.PATH_MENU
import com.neighbourschef.customer.util.common.PATH_ORDERS
import com.neighbourschef.customer.util.common.PATH_USERS
import com.neighbourschef.customer.util.common.Result
import com.neighbourschef.customer.util.common.asResultFlow
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

    fun getMenu(day: String): Flow<Result<List<Product>, Exception>> = databaseReference.child(PATH_MENU).child(day)
        .asResultFlow<List<@JvmSuppressWildcards Product>, List<@JvmSuppressWildcards Product>>(listOf()) { it }

    fun getOrders(uid: String): Flow<Result<List<Order>, Exception>> = databaseReference.child(PATH_ORDERS).child(uid)
        .asResultFlow<HashMap<String, @JvmSuppressWildcards Order>, List<@JvmSuppressWildcards Order>>(listOf()) {
            it?.values?.toList()
        }

    fun saveOrder(order: Order, uid: String) {
        databaseReference.child(PATH_ORDERS).child(uid).child(order.id).setValue(order)
    }

    fun getUser(uid: String): Flow<Result<User, Exception>> = databaseReference.child(PATH_USERS).child(uid)
        .asResultFlow<User, User>(User()) { it }

    fun saveUser(user: User, uid: String) {
        databaseReference.child(PATH_USERS).child(uid).setValue(user)
    }
}
