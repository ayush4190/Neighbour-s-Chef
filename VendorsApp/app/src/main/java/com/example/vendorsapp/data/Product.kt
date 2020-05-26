package com.example.vendorsapp.data

import com.google.firebase.database.Exclude

data class Product(
    var id: String?=null,

    var name: String?= null,

    var price: String? =null,

    var quantity: String? = null
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Product) {
            other.id == id

        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}