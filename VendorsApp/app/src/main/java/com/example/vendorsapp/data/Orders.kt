package com.example.vendorsapp.data

import android.os.ParcelUuid
import java.time.LocalDateTime

data class Orders (
    val id: String? =null,
    val name : String?=null,
    val quantity: String?= null,
    val total: String?=null,
    val dateTime: String?=null,
    val deliveryaddress:String?=null

)