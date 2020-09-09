package com.neighbourschef.customer.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T)

    @Update
    fun update(vararg items: T)

    @Delete
    fun delete(vararg items: T)
}
