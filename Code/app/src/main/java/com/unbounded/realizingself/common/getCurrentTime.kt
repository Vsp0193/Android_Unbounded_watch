package com.unbounded.realizingself.common

import java.util.Calendar

fun getCurrentTime(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val amPm = if (hour < 12) "AM" else "PM"
    val hourFormatted = if (hour > 12) hour - 12 else hour
    val minuteFormatted = minute.toString().padStart(2, '0')
    return "$hourFormatted:$minuteFormatted $amPm"
}