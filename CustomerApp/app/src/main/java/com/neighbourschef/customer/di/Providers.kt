package com.neighbourschef.customer.di

import android.content.Context
import android.content.SharedPreferences
import com.neighbourschef.customer.util.common.PREFERENCE_FILE_KEY

fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
