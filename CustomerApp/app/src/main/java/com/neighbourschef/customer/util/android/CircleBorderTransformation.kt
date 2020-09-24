package com.neighbourschef.customer.util.android

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.applyCanvas
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import kotlin.math.min

/**
 * A [Transformation] that draws a white circular border around the bitmap.
 */
class CircleBorderTransformation(
    @Px private val borderSize: Float = 5f,
    @ColorInt private val borderColor: Int = Color.BLACK
): Transformation {
    init {
        require(borderSize > 0f) { "Border size must be > 0" }
    }

    override fun key(): String = "${CircleBorderTransformation::class.java.name}-$borderSize"

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            strokeWidth = borderSize
            style = Paint.Style.STROKE
            color = borderColor
        }

        val minSize = min(input.width + borderSize, input.height + borderSize)
        val radius = minSize / 2f
        val output = pool.get(minSize.toInt(), minSize.toInt(), input.config)
        output.applyCanvas {
            drawBitmap(input, radius - input.width / 2f, radius - input.height / 2f, paint)
            drawCircle(radius, radius, radius - borderSize, paint)
        }

        return output
    }
}
