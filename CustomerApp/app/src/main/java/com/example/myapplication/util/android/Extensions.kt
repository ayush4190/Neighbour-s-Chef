@file:JvmName("Extensions")
package com.example.myapplication.util.android

import com.example.myapplication.model.Product
import com.example.myapplication.util.common.State
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Attaches a [ValueEventListener] to a [Query] and sends data into a flow
 * @receiver Firebase [Query] (a [DatabaseReference])
 * @return [Flow] emitting [State] objects depending on the data received
 */
@Suppress("ThrowableNotThrown")
@ExperimentalCoroutinesApi
fun Query.listen(): Flow<State> = callbackFlow {
    offer(State.Loading)
    val valueListener = object: ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            // Send error so that it can be processed by UI and close channel with the error
            offer(State.Failure(error.toException()))
            close(error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Exception caught is most likely a deserialization exception
            try {
                // Data is received as a map with {id, product}
                val data = dataSnapshot.getValue<Map<String, @JvmSuppressWildcards Product>>()
                if (data == null) {
                    offer(State.Failure("No data found"))
                } else {
                    val products: MutableList<Product> = mutableListOf()
                    for ((_, value) in data) {
                        value.log()
                        products.add(value)
                    }
                    offer(State.Success(products))
                }
            } catch (e: Exception) {
                // Exception is forwarded only if offer or send is called and the channel is not closed
                if (!isClosedForSend) {
                    offer(State.Failure(e))
                }
            }
        }
    }
    addValueEventListener(valueListener)
    awaitClose { removeEventListener(valueListener) }
}