package com.marlonmafra.coronavirustrackingapp.extensions

import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import com.google.android.material.snackbar.Snackbar

class SnackBar {
    companion object {
        fun make(@NonNull view: View, @NonNull text: String, @IntRange(from = 0) duration: Int): Snackbar =
            Snackbar.make(view, text, duration)
    }
}
