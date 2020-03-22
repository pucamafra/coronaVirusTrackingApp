package com.marlonmafra.coronavirustrackingapp.extensions

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val formatter = DecimalFormat("#,###.##")

fun Long.format(): String = formatter.format(this)

fun Long.formatTo(mask: String): String =
    SimpleDateFormat(mask, Locale.getDefault()).format(Date(this))