package com.neighbourschef.vendor.util.android

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
