@file:JvmName("Constants")
package com.example.myapplication.util.common

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

private const val PACKAGE_NAME = "com.example.myapplication"

// Extras
const val EXTRA_PRODUCT = "${PACKAGE_NAME}.EXTRA_PRODUCT"
const val EXTRA_CART = "${PACKAGE_NAME}.EXTRA_CART"

// Preferences
const val PREFERENCE_FILE_KEY = "$PACKAGE_NAME.customer_prefs"
const val PREFERENCE_CART = "$PACKAGE_NAME.PREFERENCE_CART"
const val PREFERENCE_USER = "$PACKAGE_NAME.PREFERENCE_USER"

// Request codes
const val RC_SIGN_IN = 1001

// Firebase constants
const val PATH_DEV = "Development"
const val PATH_TODAY = "Today's menu"
const val PATH_TOMORROW = "Tomorrows menu"
const val PATH_REST_OF_THE_WEEK = "Rest of the week"


// Misc
const val DEV_EMAIL = "a.ayushkumar1997@gmail.com"
val JSON = Json(JsonConfiguration.Stable.copy(encodeDefaults = true, isLenient = true, prettyPrint = true))