package com.example.myapplication.model

import android.os.Parcel
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    val name: String,
    @PrimaryKey val email: String,
    var phoneNumber: String,
    @Embedded var address: Address
): KParcelable {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (name != other.name) return false
        if (email != other.email) return false
        if (phoneNumber != other.phoneNumber) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int = email.hashCode()

    companion object {
        @JvmField val CREATOR = parcelableCreator(::User)
    }
}