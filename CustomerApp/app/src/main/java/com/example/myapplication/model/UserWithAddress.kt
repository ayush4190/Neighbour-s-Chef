package com.example.myapplication.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithAddress(
    @Embedded val user: User,
    @Relation(
        parentColumn = "email",
        entityColumn = "userEmail"
    ) val addresses: List<Address>
)