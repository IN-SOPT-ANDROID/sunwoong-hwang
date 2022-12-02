package org.sopt.sample.util.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, isShort: Boolean = true) {
    Toast.makeText(this, message, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}