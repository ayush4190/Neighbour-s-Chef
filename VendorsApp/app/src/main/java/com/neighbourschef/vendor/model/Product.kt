package com.neighbourschef.vendor.model

import android.os.Parcel
import com.neighbourschef.vendor.util.android.KParcelable
import com.neighbourschef.vendor.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var id: String,
    var name: String,
    var price: String,
    var quantity: String
): KParcelable {
    constructor(): this("", "", "", "")

    private constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeString(price)
        dest.writeString(quantity)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}
