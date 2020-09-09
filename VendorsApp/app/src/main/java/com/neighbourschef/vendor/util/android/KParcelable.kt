package com.neighbourschef.vendor.util.android

import android.os.Parcel
import android.os.Parcelable

interface KParcelable: Parcelable {
    override fun writeToParcel(dest: Parcel, flags: Int)
    override fun describeContents(): Int = 0
}

inline fun <reified T> parcelableCreator(crossinline create: (Parcel) -> T) =
    object: Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel): T = create(source)
        override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
    }
