package com.example.myapplication.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.Cart
import com.example.myapplication.util.common.JSON
import com.example.myapplication.util.common.PREFERENCE_CART
import com.example.myapplication.util.common.PREFERENCE_FILE_KEY

fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

fun provideCart(sharedPreferences: SharedPreferences): Cart =
    JSON.parse(
        Cart.serializer(),
        sharedPreferences.getString(
            PREFERENCE_CART,
            JSON.stringify(
                Cart.serializer(),
                Cart.EMPTY
            )
        )!!
    )

fun provideCustomerDatabase(context: Context): CustomerDatabase =
    Room.databaseBuilder(
        context,
        CustomerDatabase::class.java,
        "${context.getString(R.string.app_name)}_customer.db"
    ).build()