package com.example.myapplication.db

import android.os.ParcelUuid
import androidx.room.TypeConverter
import com.example.myapplication.model.Order
import com.example.myapplication.model.Product
import com.example.myapplication.util.common.JSON
import kotlinx.serialization.builtins.list
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class CustomConverters {
    @TypeConverter
    fun parcelUuidToString(parcelUuid: ParcelUuid): String = parcelUuid.toString()

    @TypeConverter
    fun stringToParcelUuid(string: String): ParcelUuid = ParcelUuid.fromString(string)

    @TypeConverter
    fun orderStatusToString(orderStatus: Order.OrderStatus) = orderStatus.name

    @TypeConverter
    fun stringToOrderStatus(string: String): Order.OrderStatus = Order.OrderStatus.valueOf(string)

    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long =
        localDateTime.withNano(0).toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun longToLocalDateTime(long: Long): LocalDateTime =
        LocalDateTime.ofEpochSecond(long, 0, ZoneOffset.UTC)

    @TypeConverter
    fun productListToString(products: List<Product>): String =
        JSON.stringify(Product.serializer().list, products)

    @TypeConverter
    fun stringToProductList(string: String): List<Product> =
        JSON.parse(Product.serializer().list, string)
}