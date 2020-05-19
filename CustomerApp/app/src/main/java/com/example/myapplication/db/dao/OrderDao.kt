package com.example.myapplication.db.dao

import android.os.ParcelUuid
import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
abstract class OrderDao: BaseDao<Order> {
    @Query("SELECT * FROM `order`")
    abstract fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM `order` WHERE id=:id")
    abstract suspend fun getOrderById(id: ParcelUuid): Order

    fun getAllOrdersDistinctUntilChanged() = getAllOrders().distinctUntilChanged()
}