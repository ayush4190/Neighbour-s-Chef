package com.example.myapplication.util.android

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.myapplication.model.Cart
import com.example.myapplication.util.common.JSON
import com.example.myapplication.util.common.PREFERENCE_CART
import com.example.myapplication.util.common.PREFERENCE_PROFILE_SET_UP

fun saveCart(sharedPreferences: SharedPreferences, cart: Cart) =
    sharedPreferences.edit {
        putString(PREFERENCE_CART, JSON.stringify(Cart.serializer(), cart))
    }

fun getCart(sharedPreferences: SharedPreferences): Cart =
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

fun isProfileSetup(sharedPreferences: SharedPreferences): Boolean =
    sharedPreferences.getBoolean(PREFERENCE_PROFILE_SET_UP, false)