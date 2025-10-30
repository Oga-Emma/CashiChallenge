package com.example.cashichallenge.util

import android.icu.text.DateFormat
import java.util.Date

actual fun getDateTime(time: Long): String? {
    try {
        val sdf = DateFormat.getDateInstance()
        val netDate = Date(time)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}
