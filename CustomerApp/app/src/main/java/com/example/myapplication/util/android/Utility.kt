@file:JvmName("Utility")
package com.example.myapplication.util.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.myapplication.util.common.DEV_EMAIL

fun sendEmail(context: Context, subject: String?, message: String?) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(DEV_EMAIL))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}
