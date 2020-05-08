package com.example.myapplication.model

import android.os.Parcel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Address(
    @PrimaryKey val name: String,
    val userEmail: String,
    val flatNo: String?,
    val building: String,
    val street: String,
    val locality: String,
    val city: String,
    val pinCode: String,
    val landmark: String?
): KParcelable {
    private constructor(parcel: Parcel): this(
        parcel.readString()!!,
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
        writeString(name)
        writeString(userEmail)
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
        append(building).append(", ").append(street).append(", ").appendln(locality)
        appendln(city).append("-").append(pinCode)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Address)
    }
}