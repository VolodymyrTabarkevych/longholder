package com.traday.longholder.extensions

import com.traday.longholder.utils.EMPTY_STRING
import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_DATE_FORMAT_PATTERN = "yyyy'-'MM'-'dd'T'HH':'mm':'ss"
private const val CLIENT_DATE_FORMAT_PATTERN = "dd/MM/yyyy"

fun String.formatDateServerFormatToClientFormatOrEmpty(): String {
    val serverDate = SimpleDateFormat(SERVER_DATE_FORMAT_PATTERN, Locale.getDefault()).parse(this)
        ?: return EMPTY_STRING
    return SimpleDateFormat(CLIENT_DATE_FORMAT_PATTERN, Locale.getDefault()).format(serverDate)
}

fun String.formatDateClientFormatToServerFormatOrEmpty(): String {
    val serverDate = SimpleDateFormat(CLIENT_DATE_FORMAT_PATTERN, Locale.getDefault()).parse(this)
        ?: return EMPTY_STRING
    return SimpleDateFormat(SERVER_DATE_FORMAT_PATTERN, Locale.getDefault()).format(serverDate)
}