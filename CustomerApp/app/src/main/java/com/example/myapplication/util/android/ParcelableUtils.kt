package com.example.myapplication.util.android

import android.os.Parcel
import android.os.Parcelable

/**
 * Simple interface to implement while handling Parcelable models in Kotlin
 */
interface KParcelable: Parcelable {
    override fun writeToParcel(dest: Parcel, flags: Int)
    override fun describeContents(): Int = 0
}

/**
 * Creates a [Parcelable.Creator] for a given class
 * @param T type of object for which [Parcelable.Creator] is required
 * @param create function that takes a [Parcel] and provides an object of type [T]
 *
 * @return [Parcelable.Creator] for [T]
 */
inline fun <reified T> parcelableCreator(crossinline create: (Parcel) -> T) =
    object: Parcelable.Creator<T> {
        override fun createFromParcel(source: Parcel): T = create(source)
        override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
    }