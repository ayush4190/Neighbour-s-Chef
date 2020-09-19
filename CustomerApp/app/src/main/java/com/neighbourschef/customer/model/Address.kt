package com.neighbourschef.customer.model

import android.os.Parcel
import com.neighbourschef.customer.util.android.KParcelable
import com.neighbourschef.customer.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val flatNo: String?,
    val building: String,
    val street: String,
    val locality: String,
    val city: String,
    val pinCode: String,
    val landmark: String?
): KParcelable {
    constructor(): this(null, "", "", "", "", "", null)

    private constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(flatNo)
        writeString(building)
        writeString(street)
        writeString(locality)
        writeString(city)
        writeString(pinCode)
        writeString(landmark)
    }

    fun formattedString(): String = buildString {
        flatNo?.let { append("$it, ") }
        append(building).append(", ").append(street).append(", ").appendLine(locality)
        append(city).append("-").append(pinCode)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Address)

        @JvmField val EMPTY = Address(null, "", "", "", "", "", null)
    }
}
