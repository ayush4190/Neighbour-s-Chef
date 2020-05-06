package com.example.myapplication.model

import android.os.Parcel
import android.os.ParcelUuid
import com.example.myapplication.model.Order.OrderStatus.*
import com.example.myapplication.util.android.*
import org.threeten.bp.LocalDateTime
import java.util.*

val tempProducts: MutableList<Product> = mutableListOf()

// NOTE: ParcelUuid is a parcelable wrapper around UUID. UUIDs generated are unique and so are good
// for primary identifiers

data class Order(
    val id: ParcelUuid,
    val products: List<Product>,
    val status: OrderStatus,
    val timestamp: LocalDateTime
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

        // App-controlled statuses
        READY,
        DELIVERED;

        override fun writeToParcel(dest: Parcel, flags: Int) = dest.writeString(name)

        override fun toString(): String = name

        companion object {
            @JvmField val CREATOR = parcelableCreator { valueOf(it.readString()!!) }
        }
    }

    private constructor(source: Parcel): this(
        source.readParcelable<ParcelUuid>(ParcelUuid::class.java.classLoader)!!,
        source.readParcelableListCompat<Product>(tempProducts, Product::class.java.classLoader),
        source.readEnum<OrderStatus>(),
        source.readLocalDateTime()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(id, flags)
        writeParcelableListCompat(products, flags)
        writeEnum(status)
        writeLocalDateTime(timestamp)
    }

    fun totalQuantity(): Int = products.sumBy { it.quantity }

    fun totalPrice(): Double = products.sumByDouble { it.price }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Order)

        @JvmStatic fun fromCart(cart: Cart): Order = Order(
            ParcelUuid(UUID.randomUUID()),
            cart.products,
            PLACED,
            LocalDateTime.now()
        )
    }
}