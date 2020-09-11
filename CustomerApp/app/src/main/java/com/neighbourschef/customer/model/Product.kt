package com.neighbourschef.customer.model

import android.os.Parcel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neighbourschef.customer.util.android.KParcelable
import com.neighbourschef.customer.util.android.parcelableCreator
import com.neighbourschef.customer.util.android.readBool
import com.neighbourschef.customer.util.android.writeBool
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    // quantity is intended only for customer and not vendor
    // vendor uploaded quantity is always 0
    var quantity: Int = 0,
    val veg: Boolean
): KParcelable {
    private constructor(source: Parcel): this(
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readInt(),
        source.readBool()
    )

    constructor(): this("", "", 0.0, 0, true)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeDouble(price)
        dest.writeInt(quantity)
        dest.writeBool(veg)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}

fun List<Product>.total(): Double = sumByDouble { (it.price * it.quantity) }
