package com.neighbourschef.customer.model

import android.os.Parcel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.neighbourschef.customer.util.android.KParcelable
import com.neighbourschef.customer.util.android.parcelableCreator
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    var quantity: Int = 0
): KParcelable {
    private constructor(source: Parcel): this(
        source.readString()!!,
        source.readString()!!,
        source.readDouble(),
        source.readInt()
    )

    constructor(): this("", "", 0.0, 0)

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(name)
        dest.writeDouble(price)
        dest.writeInt(quantity)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Product)
    }
}

fun List<Product>.total(): Double = sumByDouble { (it.price * it.quantity) }
