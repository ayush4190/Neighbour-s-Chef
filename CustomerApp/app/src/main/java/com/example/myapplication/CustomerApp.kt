package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.appModule
import com.example.myapplication.util.android.HyperlinkedDebugTree
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

class CustomerApp: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        importAll(androidXModule(this@CustomerApp), appModule(this@CustomerApp))
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())
    }
}