package com.example.myapplication.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.example.myapplication.util.android.base.NoInternetException
import com.example.myapplication.util.android.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NetworkStateInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if ((context.getSystemService<ConnectivityManager>()?.isNetworkAvailable()) == false)
            throw NoInternetException("Make sure you have an active data connection")
        return chain.proceed(chain.request())
    }
}