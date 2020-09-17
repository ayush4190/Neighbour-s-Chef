package com.neighbourschef.vendor.util.android

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

inline fun Fragment.toast(message: () -> String) =
    Toast.makeText(requireContext(), message(), Toast.LENGTH_SHORT).show()

fun Fragment.snackbar(message: String, actionString: String? = null, listener: ((View) -> Unit)? = null) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
    actionString?.let {
        snackbar.setAction(actionString, listener)
    }
    snackbar.show()
}

fun EditText.asString(): String = text.toString()

fun EditText.isEmpty(): Boolean = text.isBlank()
