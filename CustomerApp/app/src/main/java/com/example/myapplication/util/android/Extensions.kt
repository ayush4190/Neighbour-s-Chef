@file:JvmName("Extensions")
package com.example.myapplication.util.android

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.EditText
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
                // Data is received as a list of products
                val data = dataSnapshot.getValue<List<@JvmSuppressWildcards Product>>()
                if (data == null) {
                    offer(State.Failure("No data found"))
                } else {
                    offer(State.Success(data.toMutableList()))
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

/**
 * Retrieve the [EditText] value as a [String]
 */
fun EditText.asString(): String = text.toString()

fun View.showIf(constraint: Boolean) {
    visibility = if (constraint) View.VISIBLE else View.GONE
}

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