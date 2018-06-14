package com.example.my_pc.hauhau.utils.extensions

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by my_pc on 11/06/2018.
 */

fun View.snack(message: String, color: Int? = null, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, message, length)
    val snackView = snack.view
    snackView.setBackgroundColor(color ?: Color.RED)
    snack.setActionTextColor(Color.WHITE)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color.let { setActionTextColor(color!!) }
}