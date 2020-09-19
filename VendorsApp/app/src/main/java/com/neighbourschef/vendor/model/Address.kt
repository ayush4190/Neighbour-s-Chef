package com.neighbourschef.vendor.model

import android.os.Parcel
import com.neighbourschef.vendor.util.android.KParcelable
import com.neighbourschef.vendor.util.android.parcelableCreator

data class Address(
    val addressName: String,
    val flatNo: String?,
    val building: String,
    val street: String,
    val locality: String,
    val city: String,
    val pinCode: String,
    val landmark: String?
): KParcelable {
    constructor(): this("", null, "", "", "", "", "", null)

    private constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(addressName)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Address) return false

        if (addressName != other.addressName) return false
        if (flatNo != other.flatNo) return false
        if (building != other.building) return false
        if (street != other.street) return false
        if (locality != other.locality) return false
        if (city != other.city) return false
        if (pinCode != other.pinCode) return false
        if (landmark != other.landmark) return false

        return true
    }

    override fun hashCode(): Int = addressName.hashCode()

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Address)
    }
}
