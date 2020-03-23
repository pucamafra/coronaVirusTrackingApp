package com.marlonmafra.coronavirustrackingapp.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showSnackBar(rootLayout: View, text: Int) {
    Snackbar.make(rootLayout, text, Snackbar.LENGTH_LONG).show()
}

fun AppCompatActivity.showSnackBar(rootLayout: View, text: String) {
    Snackbar.make(rootLayout, text, Snackbar.LENGTH_LONG).show()
}