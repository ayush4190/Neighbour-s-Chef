package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.db.dao.OrderDao
import com.example.myapplication.db.dao.UserDao
import com.example.myapplication.model.Address
import com.example.myapplication.model.Order
import com.example.myapplication.model.Product
import com.example.myapplication.model.User

@Database(entities = [User::class, Address::class, Order::class, Product::class], version = 1)
@TypeConverters(CustomConverters::class)
abstract class CustomerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao
}