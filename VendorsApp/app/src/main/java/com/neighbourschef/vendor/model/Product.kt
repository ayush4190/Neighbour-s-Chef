package com.neighbourschef.vendor.model

import android.os.Parcel
import com.neighbourschef.vendor.util.android.KParcelable
import com.neighbourschef.vendor.util.android.parcelableCreator
import com.neighbourschef.vendor.util.android.readBool
import com.neighbourschef.vendor.util.android.writeBool
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: Double,
    // quantity is intended only for customer and not vendor
    // vendor uploaded quantity is always 0
    var quantity: Int = 0,
    val veg: Boolean,
    val day: String
): KParcelable {
    private constructor(source: Parcel): this(
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readInt(),
        source.readBool(),
        source.readString()!!
    )

    constructor(): this("", "", 0.0, 0, true, "")

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeDouble(price)
        dest.writeInt(quantity)
        dest.writeBool(veg)
        dest.writeString(day)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}

fun List<Product>.total(): Double = sumByDouble { (it.price * it.quantity) }
