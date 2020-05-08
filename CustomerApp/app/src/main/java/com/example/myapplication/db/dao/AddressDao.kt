package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.myapplication.model.Address
import com.example.myapplication.model.UserWithAddress
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao: BaseDao<Address> {
    @Transaction
    @Query("SELECT * FROM User LIMIT 1")
    fun loadAddresses(): Flow<UserWithAddress?>
}