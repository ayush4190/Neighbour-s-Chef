package com.neighbourschef.customer.util.android

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

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

/**
 * Read list of [Parcelable] objects
 * The Android library provides this as [Parcel.readParcelableList], however it was added in API level 29
 *
 * @see Parcel.readParcelableList
 */
inline fun <reified T: Parcelable> Parcel.readParcelableListCompat(list: MutableList<T>, cl: ClassLoader?): List<T> {
    val n = readInt()
    if (n == -1) {
        list.clear()
        return list
    }

    val m = list.size
    var i = 0
    while (i < m && i < n) {
        list[i] = readParcelable<Parcelable>(cl) as T
        i++
    }
    while (i < n) {
        list.add(readParcelable<Parcelable>(cl) as T)
        i++
    }
    while (i < m) {
        list.removeAt(n)
        i++
    }
    return list
}

/**
 * Write list of [Parcelable] objects
 * The Android library provides this as [Parcel.writeParcelableList], however it was added in API level 29
 *
 * @see Parcel.writeParcelableList
 */
inline fun <reified T: Parcelable> Parcel.writeParcelableListCompat(value: List<T>?, flags: Int) {
    if (value == null) {
        writeInt(-1)
        return
    }

    val n: Int = value.size
    var i = 0
    writeInt(n)
    while (i < n) {
        writeParcelable(value[i], flags)
        i++
    }
}

/**
 * Read [LocalDateTime] from [Parcel]
 */
fun Parcel.readLocalDateTime(): LocalDateTime =
    LocalDateTime.ofEpochSecond(readLong(), 0, ZoneOffset.UTC)

/**
 * Write [LocalDateTime] to [Parcel]
 * [LocalDateTime.withNano] is done so that the read and write values are exactly the same. This
 * can be confirmed with [LocalDateTime.isEqual]
 */
fun Parcel.writeLocalDateTime(value: LocalDateTime) {
    writeLong(value.withNano(0).toEpochSecond(ZoneOffset.UTC))
}

/**
 * Read an [Enum] from [Parcel]
 * The written value is the name of the enum
 */
inline fun <reified T: Enum<T>> Parcel.readEnum(): T = enumValueOf(readString()!!)

/**
 * Write an [Enum] to [Parcel]
 * Writes the name of the enum to the parcel
 */
inline fun <reified T: Enum<T>> Parcel.writeEnum(value: T) = writeString(value.name)
