package com.toannguyen.weatherapp.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_PATTERN_1 = "EEE, dd MMM yyyy"

fun Long.formatTimeByPattern(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}