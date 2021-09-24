package com.dz.movietmdp.common

import java.text.SimpleDateFormat

fun String.toDateFormat(): String {
    val oldFormat = SimpleDateFormat("yyyy-MM-dd")
    val oldDate = oldFormat.parse(this)
    val newFormat = SimpleDateFormat("dd MMMM yyyy")
    return newFormat.format(oldDate)
}