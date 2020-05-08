package com.example.myapplication.db.dao

import androidx.room.Query
import com.example.myapplication.model.User

interface UserDao: BaseDao<User> {
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser(email: String): User
}