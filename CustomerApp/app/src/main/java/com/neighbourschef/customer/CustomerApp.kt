package com.neighbourschef.customer

import android.app.Application
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.content.getSystemService
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.customer.di.sharedPreferencesModule
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.util.android.HyperlinkedDebugTree
import com.neighbourschef.customer.util.android.isNetworkAvailable
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import com.neighbourschef.customer.util.common.PREFERENCE_THEME
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CustomerApp: Application() {
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())

        startKoin {
            androidContext(this@CustomerApp)
            modules(sharedPreferencesModule)
        }

        if (!sharedPreferences.contains(PREFERENCE_CART)) {
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.encodeToString(Cart.serializer(), Cart()))
            }
        }
        AppCompatDelegate.setDefaultNightMode(
            sharedPreferences.getInt(PREFERENCE_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        )

        if ((getSystemService<ConnectivityManager>())?.isNetworkAvailable() == false) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
