package com.neighbourschef.vendor

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.vendor.util.common.HyperlinkedDebugTree
import timber.log.Timber

class VendorsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())
    }
}
