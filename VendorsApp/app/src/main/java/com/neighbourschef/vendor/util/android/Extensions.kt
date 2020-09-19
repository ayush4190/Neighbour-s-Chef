package com.neighbourschef.vendor.util.android

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.getSystemService
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

fun Fragment.closeKeyboard() {
    val view = requireActivity().currentFocus
    view?.let {
        requireActivity().getSystemService<InputMethodManager>()
            ?.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
    }
}

fun EditText.asString(): String = text.toString()

fun EditText.isEmpty(): Boolean = text.isBlank()
