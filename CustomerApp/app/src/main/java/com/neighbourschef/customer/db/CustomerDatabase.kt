package com.neighbourschef.customer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neighbourschef.customer.db.dao.OrderDao
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.model.Product

@Database(entities = [Order::class, Product::class], version = 1)
@TypeConverters(CustomConverters::class)
abstract class CustomerDatabase: RoomDatabase() {
    abstract fun orderDao(): OrderDao
}
