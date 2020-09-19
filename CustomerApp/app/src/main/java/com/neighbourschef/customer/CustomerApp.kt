package com.neighbourschef.customer

import android.app.Application
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.getSystemService
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.customer.di.sharedPreferencesModule
import com.neighbourschef.customer.util.android.HyperlinkedDebugTree
import com.neighbourschef.customer.util.android.isNetworkAvailable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CustomerApp: Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())

        startKoin {
            androidContext(this@CustomerApp)
            modules(sharedPreferencesModule)
        }

        if ((getSystemService<ConnectivityManager>())?.isNetworkAvailable() == false) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
