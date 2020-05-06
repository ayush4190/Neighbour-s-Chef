package com.example.myapplication.model

import java.io.Serializable

class UserProfile : Serializable {
    var user_name: String? = null
    var email: String? = null
    var mobile: String? = null
    var address: String? = null

    constructor() {}
    constructor(
        user_name: String?,
        email: String?,
        mobile: String?,
        address: String?
    ) {
        this.user_name = user_name
        this.email = email
        this.mobile = mobile
        this.address = address
    }

}