package com.nsicyber.coiintrackerapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String?.formatDate(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en"))
    val outputFormat = SimpleDateFormat("dd MMMM yyyy - HH.mm",  Locale("en"))

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}