@file:JvmName("Extensions")
package com.neighbourschef.customer.util.android

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Retrieve the [EditText] value as a [String]
 */
fun EditText.asString(): String = text.toString()

fun EditText.isEmpty(): Boolean = text.isBlank()

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
