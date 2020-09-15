package com.neighbourschef.customer.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single {
        provideSharedPreferences(androidContext())
    }
}
