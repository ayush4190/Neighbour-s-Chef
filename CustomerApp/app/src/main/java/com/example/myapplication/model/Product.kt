package com.example.myapplication.model

import android.os.Parcel
import android.os.ParcelUuid
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator

data class Product(
    val id: ParcelUuid,
    val name: String,
    val price: Double,
    var quantity: Int = 0
): KParcelable {
    private constructor(source: Parcel) : this(
        source.readParcelable<ParcelUuid>(ParcelUuid::class.java.classLoader)!!,
        source.readString()!!,
        source.readDouble(),
        source.readInt()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(id, flags)
        dest.writeString(name)
        dest.writeDouble(price)
        dest.writeInt(quantity)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}