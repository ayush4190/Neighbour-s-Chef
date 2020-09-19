package com.neighbourschef.customer.util.android

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import com.neighbourschef.customer.util.common.EMAIL_DEV

/**
 * Creates an IntentChooser for sending emails
 * @param context context required to launch intent and start activity
 * @param subject subject of the email
 * @param message content of the email
 */
fun sendEmail(context: Context, subject: String, message: String) {
    // ACTION_SENDTO and "mailto:" ensure that only email clients can receive this intent
    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
        .putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_DEV))
        .putExtra(Intent.EXTRA_SUBJECT, subject)
        .putExtra(Intent.EXTRA_TEXT, message)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No email clients found", Toast.LENGTH_SHORT).show()
    }

}
