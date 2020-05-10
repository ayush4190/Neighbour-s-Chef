package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.model.User

@Dao
interface UserDao: BaseDao<User> {
    @Transaction
    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserById(email: String): User
}