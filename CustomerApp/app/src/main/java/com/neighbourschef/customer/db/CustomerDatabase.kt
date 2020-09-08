package com.neighbourschef.customer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neighbourschef.customer.db.dao.OrderDao
import com.neighbourschef.customer.db.dao.UserDao
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.Order
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.model.User

@Database(entities = [User::class, Address::class, Order::class, Product::class], version = 1)
@TypeConverters(CustomConverters::class)
abstract class CustomerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
}