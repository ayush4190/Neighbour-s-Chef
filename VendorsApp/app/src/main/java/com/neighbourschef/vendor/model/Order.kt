package com.neighbourschef.vendor.model

import android.os.Parcel
import com.neighbourschef.vendor.model.Order.OrderStatus.CANCELLED
import com.neighbourschef.vendor.model.Order.OrderStatus.COMPLETED
import com.neighbourschef.vendor.model.Order.OrderStatus.PLACED
import com.neighbourschef.vendor.util.android.KParcelable
import com.neighbourschef.vendor.util.android.parcelableCreator
import com.neighbourschef.vendor.util.android.readEnum
import com.neighbourschef.vendor.util.android.readParcelableListCompat
import com.neighbourschef.vendor.util.android.writeEnum
import com.neighbourschef.vendor.util.android.writeParcelableListCompat
import com.neighbourschef.vendor.util.common.toLocalDateTime
import com.neighbourschef.vendor.util.common.toTimestamp
import org.threeten.bp.LocalDateTime

val tempProducts: MutableList<Product> = mutableListOf()

data class Order(
    val id: String,
    val products: List<Product>,
    var status: OrderStatus,
    val timestamp: Long,
    var comments: String
): KParcelable {

    /**
     * Models the current status of the order
     * The user can place an order which results in [PLACED]. After an order is [PLACED], it may be
     * [CANCELLED] by the user.
     *
     * Since there is no delivery model, the vendor must set the order as [COMPLETED].
     * Additionally, the app may cancel the order if some error occurs (payment, network connection
     * loss, etc.)
     */
    enum class OrderStatus: KParcelable {
        // User-controlled statuses
        PLACED,
        CANCELLED,
        // Vendor-controlled statuses
        COMPLETED;

        override fun writeToParcel(dest: Parcel, flags: Int) = dest.writeString(name)

        override fun toString(): String = name

        companion object {
            @JvmField val CREATOR = parcelableCreator { valueOf(it.readString()!!) }
        }
    }

    constructor(): this("", listOf(), PLACED, LocalDateTime.now().toTimestamp(), "")

    private constructor(source: Parcel): this(
        source.readString()!!,
        source.readParcelableListCompat<Product>(tempProducts, Product::class.java.classLoader),
        source.readEnum<OrderStatus>(),
        source.readLong(),
        source.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeParcelableListCompat(products, flags)
        writeEnum(status)
        writeLong(timestamp)
        writeString(comments)
    }

    fun totalQuantity(): Int = products.sumBy { it.quantity }

    fun totalPrice(): Double = products.total()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false

        if (id != other.id) return false
        if (products != other.products) return false
        if (status != other.status) return false
        if (timestamp != other.timestamp) return false
        if (comments != other.comments) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String =
        "Order(id=$id, products=$products, status=$status, timestamp=${timestamp.toLocalDateTime()}, comments=$comments)"

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Order)
    }
}
