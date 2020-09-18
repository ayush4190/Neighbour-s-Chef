package com.neighbourschef.vendor.util.android

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.net.toUri
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.util.common.EMAIL_DEV
import com.neighbourschef.vendor.util.common.tempFileName
import java.io.ByteArrayOutputStream

fun compressImage(context: Context, uri: Uri): ByteArrayOutputStream {
    val original = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
    val out = ByteArrayOutputStream()
    original.compress(Bitmap.CompressFormat.JPEG, 40, out)

    return out
}

fun restartApp(activity: Activity) {
    val intent = activity.packageManager
        .getLaunchIntentForPackage(activity.baseContext.packageName)!!.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    activity.startActivity(intent)
    activity.finish()
}

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

fun getPickImageIntent(context: Context, cameraPermissionGranted: Boolean): Pair<Intent?, Uri?> {
    var chooserIntent: Intent? = null
    val intentList = mutableListOf<Intent>()
    var uri: Uri? = null

    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    addIntentToList(context, intentList, galleryIntent)
    if (cameraPermissionGranted) {
        uri = setImageUri(context)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, uri)
        addIntentToList(context, intentList, cameraIntent)
    }

    if (intentList.size > 0) {
        chooserIntent = Intent.createChooser(
            intentList.removeLast(),
            context.getString(R.string.select_capture_image)
        ).putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            intentList.toTypedArray()
        )
    }
    return chooserIntent to uri
}

private fun addIntentToList(context: Context, intents: MutableList<Intent>, intent: Intent): MutableList<Intent> {
    val resInfo = context.packageManager.queryIntentActivities(intent, 0)
    for (info in resInfo) {
        intents.add(
            Intent(intent).setPackage(info.activityInfo.packageName)
        )
    }
    return intents
}

private fun setImageUri(context: Context): Uri? {
    val resolver = context.contentResolver
    val contentValues = ContentValues(3).apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, tempFileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
    }

    // Below Android Q, images are stored in private data directory which will be removed on uninstall

    // val directory = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
    // directory.mkdirs()
    //
    // val file = File(
    //     directory,
    //     getTempFileName()
    // )
    // if (file.exists()) file.delete()
    // file.createNewFile()
    // imageUri = FileProvider.getUriForFile(
    //     requireContext(),
    //     BuildConfig.APPLICATION_ID + requireContext().getString(R.string.file_provider_name),
    //     file
    // )
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
