package com.example.myapplication.db.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T)

    @Update
    fun update(vararg items: T)

    @Delete
    fun delete(vararg items: T)
}