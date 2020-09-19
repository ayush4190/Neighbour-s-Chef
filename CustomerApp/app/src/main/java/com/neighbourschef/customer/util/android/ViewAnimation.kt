package com.neighbourschef.customer.util.android

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.neighbourschef.customer.util.common.ANIM_DURATION

fun View.rotate(shouldRotate: Boolean): Boolean {
    animate().setDuration(ANIM_DURATION)
        .setListener(object : AnimatorListenerAdapter() {})
        .rotation(if (shouldRotate) 135f else 0f)
    return shouldRotate
}

fun View.showIn() {
    visibility = View.VISIBLE
    alpha = 0f
    translationY = height.toFloat()
    animate().setDuration(ANIM_DURATION)
        .translationY(0f)
        .setListener(object : AnimatorListenerAdapter() {})
        .alpha(1f)
        .start()
}

fun View.showOut() {
    visibility = View.VISIBLE
    alpha = 1f
    translationY = 0f
    animate().setDuration(ANIM_DURATION)
        .translationY(height.toFloat())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
                super.onAnimationEnd(animation)
            }
        })
        .alpha(0f)
        .start()
}

fun View.init() {
    visibility = View.GONE
    translationY = height.toFloat()
    alpha = 0f
}
