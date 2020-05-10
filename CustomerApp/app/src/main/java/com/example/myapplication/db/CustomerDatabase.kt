package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.db.dao.UserDao
import com.example.myapplication.model.Address
import com.example.myapplication.model.User

@Database(entities = [User::class, Address::class], version = 1)
abstract class CustomerDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}