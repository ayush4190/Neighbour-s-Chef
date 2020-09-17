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
    val description: String,
    val price: Double,
    // quantity is intended only for customer and not vendor
    // vendor uploaded quantity is always 0
    var quantity: Int = 0,
    val veg: Boolean,
    val day: String,
    val firebaseUrl: String?
): KParcelable {
    private constructor(source: Parcel): this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readInt(),
        source.readBool(),
        source.readString()!!,
        source.readString()
    )

    constructor(): this("", "", "", 0.0, 0, true, "", null)

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(description)
        writeDouble(price)
        writeInt(quantity)
        writeBool(veg)
        writeString(day)
        writeString(firebaseUrl)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}

fun List<Product>.total(): Double = sumByDouble { (it.price * it.quantity) }
