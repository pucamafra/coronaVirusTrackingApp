package com.marlonmafra.coronavirustrackingapp.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.formatTo(mask: String): String =
    SimpleDateFormat(mask, Locale.getDefault()).format(this)

fun Date.day(): Int {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}