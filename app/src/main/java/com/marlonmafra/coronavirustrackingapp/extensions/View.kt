package com.marlonmafra.coronavirustrackingapp.extensions

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.enablebility(enable: Boolean) {
    isEnabled = enable
}

fun View.changeVisibility(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.getPlural(id: Int, quantity: Int): String = resources.getQuantityString(id, quantity)

fun View.getPlural(id: Int, quantity: Int, vararg format: Any): String =
    resources.getQuantityString(id, quantity, *format)

fun View.getString(id: Int): String = context.getString(id)

fun View.getString(id: Int, vararg formatArgs: Any): String = context.getString(id, *formatArgs)

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun View.getDimension(id: Int): Float = context.resources.getDimension(id)

fun View.getColor(id: Int): Int = ContextCompat.getColor(context, id)

fun View.clickWithDebounce(debounceTime: Long = 1000L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val difference = SystemClock.elapsedRealtime() - lastClickTime
            when {
                difference < debounceTime -> return
                else -> action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}