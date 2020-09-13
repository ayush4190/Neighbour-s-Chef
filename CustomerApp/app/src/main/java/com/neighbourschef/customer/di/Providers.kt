package com.neighbourschef.customer.di

import android.content.Context
import android.content.SharedPreferences
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import com.neighbourschef.customer.util.common.PREFERENCE_FILE_KEY

fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

fun provideCart(sharedPreferences: SharedPreferences): Cart =
    JSON.decodeFromString(
        Cart.serializer(),
        sharedPreferences.getString(
            PREFERENCE_CART,
            JSON.encodeToString(
                Cart.serializer(),
                Cart()
            )
        )!!
    )
