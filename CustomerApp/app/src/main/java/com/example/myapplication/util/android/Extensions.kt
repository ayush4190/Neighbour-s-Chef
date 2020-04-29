@file:JvmName("Extensions")
package com.example.myapplication.util.android

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

fun Context.sendEmail(
    subject: String?,
    message: String?
) {
    val email = Intent(Intent.ACTION_SEND)
    email.setDataAndType(Uri.parse("mailto:"), "text/plain")
    email.putExtra(Intent.EXTRA_EMAIL, arrayOf("a.ayushkumar1997@gmail.com"))
    email.putExtra(Intent.EXTRA_SUBJECT, subject)
    email.putExtra(Intent.EXTRA_TEXT, message)
    try {
        startActivity(Intent.createChooser(email, "Send mail..."))
        Log.i("Finished", "")
        //            binding.helpEmailContent.setText("");
//            binding.helpEmailSubject.setText("");
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT)
            .show()
    }
}
