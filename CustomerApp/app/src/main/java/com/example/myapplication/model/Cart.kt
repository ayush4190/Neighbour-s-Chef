package com.example.myapplication.model

import android.os.Parcel
import com.example.myapplication.util.android.KParcelable
import com.example.myapplication.util.android.parcelableCreator
import com.example.myapplication.util.android.readParcelableListCompat
import com.example.myapplication.util.android.writeParcelableListCompat

data class Cart(
    val products: MutableList<Product>
): KParcelable {
    private constructor(source: Parcel): this(
        source.readParcelableListCompat<Product>(tempProducts, Product::class.java.classLoader).toMutableList()
    )

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

    fun total(): Double = products.sumByDouble { it.price }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Cart)
    }
}