package com.neighbourschef.customer.model

import android.os.Parcel
import android.os.ParcelUuid
import com.neighbourschef.customer.model.Order.OrderStatus.CANCELLED
import com.neighbourschef.customer.model.Order.OrderStatus.DELIVERED
import com.neighbourschef.customer.model.Order.OrderStatus.PLACED
import com.neighbourschef.customer.model.Order.OrderStatus.READY
import com.neighbourschef.customer.util.android.KParcelable
import com.neighbourschef.customer.util.android.parcelableCreator
import com.neighbourschef.customer.util.android.readEnum
import com.neighbourschef.customer.util.android.readLocalDateTime
import com.neighbourschef.customer.util.android.readParcelableListCompat
import com.neighbourschef.customer.util.android.writeEnum
import com.neighbourschef.customer.util.android.writeLocalDateTime
import com.neighbourschef.customer.util.android.writeParcelableListCompat
import org.threeten.bp.LocalDateTime
import java.util.UUID

val tempProducts: MutableList<Product> = mutableListOf()

// NOTE: ParcelUuid is a parcelable wrapper around UUID. UUIDs generated are unique and so are good
// for primary identifiers

data class Order(
    val id: ParcelUuid,
    val products: List<Product>,
    var status: OrderStatus,
    val timestamp: LocalDateTime,
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

    constructor(): this(ParcelUuid(UUID.randomUUID()), listOf(), PLACED, LocalDateTime.now(), "")

    private constructor(source: Parcel): this(
        source.readParcelable<ParcelUuid>(ParcelUuid::class.java.classLoader)!!,
        source.readParcelableListCompat<Product>(tempProducts, Product::class.java.classLoader),
        source.readEnum<OrderStatus>(),
        source.readLocalDateTime(),
        source.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(id, flags)
        writeParcelableListCompat(products, flags)
        writeEnum(status)
        writeLocalDateTime(timestamp)
        writeString(comments)
    }

    fun totalQuantity(): Int = products.sumBy { it.quantity }

    fun totalPrice(): Double = products.sumByDouble { it.price }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Order) return false

        if (id != other.id) return false
        if (products != other.products) return false
        if (status != other.status) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Order)

        @JvmStatic fun fromCart(cart: Cart): Order = Order(
            ParcelUuid(UUID.randomUUID()),
            cart.products,
            PLACED,
            LocalDateTime.now(),
            ""
        )
    }
}
