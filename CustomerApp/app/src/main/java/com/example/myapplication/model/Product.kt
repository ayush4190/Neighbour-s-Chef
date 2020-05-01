package com.example.myapplication.model

import android.os.Parcel
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator

/*
    TODO: Update fields to resemble real world
    user: aayush
    date: 1/5/20
*/
// Price should be a float or double
// Quantity should be an int
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