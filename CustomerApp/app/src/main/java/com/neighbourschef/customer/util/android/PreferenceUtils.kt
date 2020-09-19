package com.neighbourschef.customer.util.android

import android.content.SharedPreferences
import androidx.core.content.edit
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.util.common.JSON

fun saveCart(sharedPreferences: SharedPreferences, uid: String, cart: Cart) =
    sharedPreferences.edit {
        putString(uid, JSON.encodeToString(Cart.serializer(), cart))
    }

fun getCart(sharedPreferences: SharedPreferences, uid: String?): Cart =
    if (uid != null) {
        JSON.decodeFromString(
            Cart.serializer(),
            sharedPreferences.getString(
                uid,
                JSON.encodeToString(
                    Cart.serializer(),
                    Cart()
                )
            )!!
        )
    } else Cart()
