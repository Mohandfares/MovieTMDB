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