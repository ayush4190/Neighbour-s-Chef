package com.neighbourschef.vendor.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.model.Order
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.model.User
import com.neighbourschef.vendor.util.common.PATH_MENU
import com.neighbourschef.vendor.util.common.PATH_ORDERS
import com.neighbourschef.vendor.util.common.PATH_USERS
import com.neighbourschef.vendor.util.common.Result
import com.neighbourschef.vendor.util.common.asResultFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
object FirebaseRepository {
    private val databaseReference: DatabaseReference by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.database.reference
    }

    fun saveItem(product: Product) {
        databaseReference.child(PATH_MENU).push().setValue(product)
    }

    fun getOrders(): Flow<Result<List<Pair<String, Order>>, Exception>> = databaseReference.child(PATH_ORDERS)
        .asResultFlow<HashMap<String, HashMap<String, @JvmSuppressWildcards Order>>, List<Pair<String, Order>>>(
            listOf()
        ) {
            val list = mutableListOf<Pair<String, Order>>()

            it?.forEach { entry ->
                entry.value.forEach { innerEntry ->
                    list.add(entry.key to innerEntry.value)
                }
            }

            list
        }

    fun saveOrder(order: Order, uid: String) {
        databaseReference.child(PATH_ORDERS).child(uid).child(order.id).setValue(order)
    }

    fun getUser(uid: String): Flow<Result<User, Exception>> = databaseReference.child(PATH_USERS).child(uid)
        .asResultFlow<User, User>(User()) { it }
}