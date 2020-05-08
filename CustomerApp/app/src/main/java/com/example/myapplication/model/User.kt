package com.example.myapplication.model

import android.os.Parcel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import com.example.myapplication.util.android.readParcelableListCompat
import com.example.myapplication.util.android.writeParcelableListCompat
import kotlinx.serialization.Serializable

private val TEMP_ADDRESSES: MutableList<Address> = mutableListOf()

@Entity
@Serializable
data class User(
    val name: String,
    @PrimaryKey val email: String,
    val phoneNumber: String
): KParcelable {
    private constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(email)
        writeString(phoneNumber)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::User)
    }
}