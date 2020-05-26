package com.example.vendorsapp.data

data class OrderDetail(
    var itemname: String?=null,
    var private:String?= null,
    var quantity: String? = null,
    private var total_price: String? = null,
    private var address: String? = null,
    private var phone_no: String? = null,
    private var date: String? = null,
    private var name: String? = null
)