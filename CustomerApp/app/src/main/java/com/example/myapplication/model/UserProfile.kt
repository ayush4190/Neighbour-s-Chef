package com.example.myapplication.model

import android.os.Parcel
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: Address
): KParcelable {
    @Serializable
    data class Address(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val flatNo: String?,
        val building: String,
        val locality: String,
        val city: String,
        val pinCode: String
    ): KParcelable {
        private constructor(parcel: Parcel): this(
            parcel.readString()!!,
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeString(name)
            writeDouble(latitude)
            writeDouble(longitude)
            writeString(flatNo)
            writeString(building)
            writeString(locality)
            writeString(city)
            writeString(pinCode)
        }

        companion object {
            @JvmField val CREATOR = parcelableCreator(::Address)
        }
    }

    private constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable<Address>(Address::class.java.classLoader)!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(email)
        writeString(phoneNumber)
        writeParcelable(address, flags)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::UserProfile)
    }
}