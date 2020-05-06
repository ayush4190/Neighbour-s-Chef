package com.example.myapplication.model

import android.os.Parcel
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import com.example.myapplication.util.android.readParcelableListCompat
import com.example.myapplication.util.android.writeParcelableListCompat
import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val products: MutableList<Product>
): KParcelable {
    private constructor(source: Parcel): this(
        source.readParcelableListCompat<Product>(tempProducts, Product::class.java.classLoader).toMutableList()
    )

    constructor(): this(mutableListOf())

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelableListCompat(products, flags)
    }

    /**
     * Adds a product to the cart
     * If the product exists, increase the quantity, else add to the cart
     */
    infix operator fun plus(product: Product) {
        val index = products.indexOf(product)

        if (index == -1) {
            products += product
        } else {
            products[index].quantity++
        }
    }

    infix operator fun plusAssign(product: Product) = plus(product)

    fun total(): Double = products.sumByDouble { it.price }

    fun size(): Int = products.size

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Cart)

        @JvmField val EMPTY = Cart(mutableListOf())
    }
}