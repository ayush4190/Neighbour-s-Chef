package com.neighbourschef.vendor.util.common

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

private const val PACKAGE_NAME = "com.neighbourschef.vendor"

val tempFileName: String
    get() = "JPEG_${LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.jpg"

const val DAY_TODAY = "Today"
const val DAY_TOMORROW = "Tomorrow"

const val PATH_ORDERS = "orders"
const val PATH_USERS = "users"
const val PATH_MENU = "menu"
const val PATH_IMAGES = "img"

const val EXTRA_ORDER = "$PACKAGE_NAME.EXTRA_ORDER"
const val EXTRA_UID = "$PACKAGE_NAME.EXTRA_UID"
const val EXTRA_DAY = "$PACKAGE_NAME.EXTRA_DAY"
const val EXTRA_PRODUCT = "$PACKAGE_NAME.EXTRA_PRODUCT"

const val VEILED_ITEM_COUNT = 10

const val RC_PICK_IMAGE = 100

const val EMAIL_VENDOR = "vendor.neighbourschef@gmail.com"
const val EMAIL_DEV = ""
const val NUMBER_DEV = ""
