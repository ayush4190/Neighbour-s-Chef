@file:JvmName("Extensions")
package com.neighbourschef.customer.util.android

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.common.UiState
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
fun <E> SendChannel<E>.safeOffer(value: E): Boolean =
    !isClosedForSend && try {
        offer(value)
    } catch (e: CancellationException) {
        false
    }

/**
 * Attaches a [ValueEventListener] to a [Query] and sends data into a flow
 * @receiver Firebase [Query] (a [DatabaseReference])
 * @return [Flow] emitting [UiState] objects depending on the data received
 */
@ExperimentalCoroutinesApi
fun Query.listenMenu(): Flow<UiState> = callbackFlow {
    // Show initial loading state
    offer(UiState.Loading)

    val valueListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            // Send error so that it can be processed by UI and close channel with the error
            offer(UiState.Failure(error.toException()))
            close(error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Exception caught is most likely a deserialization exception
            try {
                // Data is received as a list of products
                val data = dataSnapshot.getValue<List<@JvmSuppressWildcards Product>>()
                offer(
                    UiState.Success(
                        data?.toMutableList() ?: mutableListOf()
                    )
                )
            } catch (e: Exception) {
                safeOffer(UiState.Failure(e))
            }
        }
    }
    addValueEventListener(valueListener)
    awaitClose { removeEventListener(valueListener) }
}

/**
 * Attaches a [ValueEventListener] to a [Query] and sends data into a flow
 * @receiver Firebase [Query] (a [DatabaseReference])
 * @return [Flow] emitting [UiState] objects depending on the data received
 */
@ExperimentalCoroutinesApi
fun Query.listenOrder(): Flow<UiState> = callbackFlow {
    // Show initial loading state
    offer(UiState.Loading)

    val valueListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            // Send error so that it can be processed by UI and close channel with the error
            offer(UiState.Failure(error.toException()))
            close(error.toException())
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Exception caught is most likely a deserialization exception
            try {
                // Data is received as a list of products
                val data = dataSnapshot.getValue<List<@JvmSuppressWildcards Order>>()
                offer(
                    UiState.Success(
                        data?.toMutableList() ?: mutableListOf()
                    )
                )
            } catch (e: Exception) {
                safeOffer(UiState.Failure(e))
            }
        }
    }
    addValueEventListener(valueListener)
    awaitClose { removeEventListener(valueListener) }
}

@ExperimentalCoroutinesApi
fun Query.listenUser(): Flow<UiState> = callbackFlow {
    offer(UiState.Loading)

    val valueListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            try {

                offer(UiState.Success(snapshot.getValue<User>()))
            } catch (e: Exception) {
                safeOffer(UiState.Failure(e))
            }
        }

        override fun onCancelled(error: DatabaseError) {
            offer(UiState.Failure(error.toException()))
            close(error.toException())
        }
    }
    addValueEventListener(valueListener)
    awaitClose { removeEventListener(valueListener) }
}

/**
 * Retrieve the [EditText] value as a [String]
 */
fun EditText.asString(): String = text.toString()

/**
 * Check if the device is connected to any internet source (mobile data, WiFi or ethernet)
 */
fun ConnectivityManager.isNetworkAvailable(): Boolean =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = this.activeNetwork
        getNetworkCapabilities(network)?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } ?: false
    } else {
        activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

fun Fragment.toast(message: String) = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

fun View.toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun Application.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
