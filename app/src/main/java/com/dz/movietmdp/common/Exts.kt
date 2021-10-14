package com.dz.movietmdp.common

import java.text.SimpleDateFormat
import java.util.*

fun String?.toDateFormat(): String {
    if (!isNullOrBlank()) {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd")
        val oldDate = oldFormat.parse(this) ?: Date()
        val newFormat = SimpleDateFormat("dd MMMM yyyy")
        return newFormat.format(oldDate)
    }
    return ""
}

fun String.reviewDateFormat(): String {
    val oldFormat = SimpleDateFormat("yyyy-MM-dd")
    val oldDate = oldFormat.parse(this.split("T")[0]) ?: Date()
    val newFormat = SimpleDateFormat("MMMMM dd, yyyy")
    return newFormat.format(oldDate)
}

fun Int?.runTime(): String {
    if (this != null) {
        val hour: Int = (this / 60)
        val minute: Int = (this % 60)
        return when {
            hour == 0 && minute == 0 -> ""
            hour == 0 && minute > 0 -> "${minute}m"
            else -> "${hour}h ${minute}m"
        }
    }
    return ""
}