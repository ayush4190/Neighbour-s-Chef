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
        val index = products.indexOfFirst { it.id == product.id }

        if (index == -1) {
            products += product
        } else {
            products[index].quantity++
        }
    }

    infix operator fun plusAssign(product: Product) = plus(product)

    infix operator fun plus(newProducts: List<Product>) {
        products.addAll(newProducts)
    }

    infix operator fun plusAssign(newProducts: List<Product>) = plus(newProducts)

    infix operator fun minus(product: Product) {
        val index = products.indexOfFirst { it.id == product.id }

        if (index != -1) {
            if (products[index].quantity > 1) {
                products[index].quantity--
            } else {
                products.removeAt(index)
            }
        }
    }

    infix operator fun minusAssign(product: Product) = minus(product)

    infix operator fun contains(product: Product): Boolean =
        products.indexOfFirst { it.id == product.id } != -1

    fun total(): Double = products.total()

    fun size(): Int = products.size

    fun isEmpty(): Boolean = size() == 0

    fun clear() = products.clear()

    companion object {
        @JvmField val CREATOR = parcelableCreator(::Cart)

        @JvmField val EMPTY = Cart(mutableListOf())
    }
}