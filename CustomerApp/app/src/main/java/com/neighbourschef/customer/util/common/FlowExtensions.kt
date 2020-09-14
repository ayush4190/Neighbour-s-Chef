package com.neighbourschef.customer.util.common

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Offer values from a [SendChannel] only if it is not closed by a [SendChannel.close] call
 * @receiver channel used to send data
 * @param E item to be sent in the channel
 * @return `true` if channel can safely send data and `false` otherwise
 */
@ExperimentalCoroutinesApi
inline fun <reified E> SendChannel<E>.safeOffer(value: E): Boolean =
    !isClosedForSend && try {
        offer(value)
    } catch (e: CancellationException) {
        false
    }

/**
 * Converts a Firebase Realtime Database query into a [Flow] with values encapsulated in [Result] objects
 * @receiver database reference at intended path
 * @param I type of data deserialized from Firebase
 * @param O type of data requested. In most cases Firebase returns a [HashMap] which must be mapped to an appropriate
 * value
 * @param onNull default value to be returned if the data is null. Firebase data is null if the path doesn't exist
 * @param mapper maps the received value from Firebase into one that is appropriate for the app
 * @return converts the listener callbacks into a [Flow] carrying [Result]-wrapped data
 */
@ExperimentalCoroutinesApi
inline fun <reified I, reified O> DatabaseReference.asResultFlow(
    onNull: O,
    crossinline mapper: (I?) -> O?
): Flow<Result<O, Exception>> = callbackFlow {
    val valueListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            // Send error so that it can be processed by UI and close channel with the error
            offer(Result.Error(error.toException()))
            close(error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Result.of wraps the operation and automatically handles exceptions
            safeOffer(
                Result.of {
                    mapper(dataSnapshot.getValue<I>()) ?: onNull
                }
            )
        }
    }
    addValueEventListener(valueListener)
    awaitClose { removeEventListener(valueListener) }
}
