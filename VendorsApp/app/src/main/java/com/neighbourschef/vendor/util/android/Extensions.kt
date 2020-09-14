package com.neighbourschef.vendor.util.android

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

inline fun Fragment.toast(message: () -> String) =
    Toast.makeText(requireContext(), message(), Toast.LENGTH_SHORT).show()

fun EditText.asString(): String = text.toString()

fun EditText.isEmpty(): Boolean = text.isBlank()
