package com.neighbourschef.customer.util.android

import android.content.SharedPreferences
import androidx.core.content.edit
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import com.neighbourschef.customer.util.common.PREFERENCE_PROFILE_SET_UP
import com.neighbourschef.customer.util.common.PREFERENCE_USER

fun saveCart(sharedPreferences: SharedPreferences, cart: Cart) =
    sharedPreferences.edit {
        putString(PREFERENCE_CART, JSON.encodeToString(Cart.serializer(), cart))
    }

fun getCart(sharedPreferences: SharedPreferences): Cart =
    JSON.decodeFromString(
        Cart.serializer(),
        sharedPreferences.getString(
            PREFERENCE_CART,
            JSON.encodeToString(
                Cart.serializer(),
                Cart.EMPTY
            )
        )!!
    )

fun isProfileSetup(sharedPreferences: SharedPreferences): Boolean =
    sharedPreferences.getBoolean(PREFERENCE_PROFILE_SET_UP, false)

fun saveUserRef(sharedPreferences: SharedPreferences, ref: String) =
    sharedPreferences.edit {
        putString(PREFERENCE_USER, ref)
    }

fun getUserRef(sharedPreferences: SharedPreferences): String? =
    sharedPreferences.getString(PREFERENCE_USER, null)
