@file:JvmName("Constants")
package com.neighbourschef.customer.util.common

import kotlinx.serialization.json.Json

private const val PACKAGE_NAME = "com.neighbourschef.customer"

// Extras
const val EXTRA_PRODUCT = "$PACKAGE_NAME.EXTRA_PRODUCT"
const val EXTRA_ITEMS = "$PACKAGE_NAME.EXTRA_ITEMS"
const val EXTRA_USER = "$PACKAGE_NAME.EXTRA_USER"
const val EXTRA_FIREBASE_UID = "$PACKAGE_NAME.EXTRA_FIREBASE_UID"
const val EXTRA_DAY = "$PACKAGE_NAME.EXTRA_DAY"

// Preferences
const val PREFERENCE_FILE_KEY = "$PACKAGE_NAME.customer_prefs"

// Request codes
const val RC_SIGN_IN = 1001

// Firebase constants
const val PATH_ORDERS = "orders"
const val PATH_USERS = "users"
const val PATH_MENU = "menu"

// Misc
const val ANIM_DURATION: Long = 200
const val VEILED_ITEM_COUNT = 10

const val DAY_TODAY = "Today"
const val DAY_TOMORROW = "Tomorrow"
const val PATH_REST_OF_THE_WEEK = "rest_of_the_week"

const val EMAIL_DEV = "rtvkiz@gmail.com"
const val NUMBER_DEV = "8989973062"
val JSON = Json {
    encodeDefaults = true
    isLenient = true
    prettyPrint = true
}
