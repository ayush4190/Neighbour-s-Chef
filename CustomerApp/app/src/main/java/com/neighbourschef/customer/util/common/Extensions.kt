package com.neighbourschef.customer.util.common

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)

fun LocalDateTime.toTimestamp(): Long = withNano(0).toEpochSecond(ZoneOffset.UTC)
