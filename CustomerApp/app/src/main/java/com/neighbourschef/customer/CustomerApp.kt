package com.neighbourschef.customer

import android.app.Application
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.getSystemService
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.customer.di.sharedPreferencesModule
import com.neighbourschef.customer.util.android.HyperlinkedDebugTree
import com.neighbourschef.customer.util.android.ReleaseTree
import com.neighbourschef.customer.util.android.isNetworkAvailable
import com.neighbourschef.customer.util.android.log
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CustomerApp: Application() {
    override fun onCreate() {
        super.onCreate()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) Timber.plant(HyperlinkedDebugTree()) else Timber.plant(ReleaseTree())

        startKoin {
            androidContext(this@CustomerApp)
            modules(sharedPreferencesModule)
        }

        if ((getSystemService<ConnectivityManager>())?.isNetworkAvailable() == false) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
