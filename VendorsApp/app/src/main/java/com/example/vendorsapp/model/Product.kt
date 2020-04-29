package com.example.vendorsapp.model

import android.os.Parcel
import com.example.vendorsapp.util.android.KParcelable
import com.example.vendorsapp.util.android.parcelableCreator

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