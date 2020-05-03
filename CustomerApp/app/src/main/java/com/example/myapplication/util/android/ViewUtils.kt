package com.example.myapplication.util.android

import android.content.Context
import android.content.Intent
import com.example.myapplication.ui.activity.login.SigninActivity

//fun Context.startHomeActivity() =
////    Intent(this, HomeActivity::class.java).also {
////        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////        startActivity(it)
//    }

fun Context.startLoginActivity() =
    Intent(this, SigninActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }