package com.example.myapplication

import android.app.Application
import com.example.myapplication.util.android.HyperlinkedDebugTree
import timber.log.Timber

class CustomerApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(HyperlinkedDebugTree())
    }
}