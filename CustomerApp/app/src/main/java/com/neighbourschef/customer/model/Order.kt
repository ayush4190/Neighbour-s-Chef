package com.neighbourschef.customer.model

import android.os.Parcel
import com.neighbourschef.customer.model.Order.OrderStatus.CANCELLED
import com.neighbourschef.customer.model.Order.OrderStatus.DELIVERED
import com.neighbourschef.customer.model.Order.OrderStatus.PLACED
import com.neighbourschef.customer.model.Order.OrderStatus.READY
import com.neighbourschef.customer.util.android.KParcelable
import com.neighbourschef.customer.util.android.parcelableCreator
import com.neighbourschef.customer.util.android.readEnum
import com.neighbourschef.customer.util.android.readParcelableListCompat
import com.neighbourschef.customer.util.android.writeEnum
import com.neighbourschef.customer.util.android.writeParcelableListCompat
import com.neighbourschef.customer.util.common.toLocalDateTime
import com.neighbourschef.customer.util.common.toTimestamp
import org.threeten.bp.LocalDateTime
import java.util.UUID

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
     * Once the order can be sent for delivery, it is [READY] and finally it is [DELIVERED].
     * Additionally, the app may cancel the order if some error occurs (payment, network connection
     * loss, etc.)
     */
    enum class OrderStatus: KParcelable {
        // User-controlled statuses
        PLACED,
        CANCELLED,

        // Vendor-controlled statuses
        READY,
        DELIVERED;

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

        @JvmStatic fun fromCart(cart: Cart): Order = Order(
            UUID.randomUUID().toString(),
            cart.products,
            PLACED,
            LocalDateTime.now().toTimestamp(),
            ""
        )
    }
}
