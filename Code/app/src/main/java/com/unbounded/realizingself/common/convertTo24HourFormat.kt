package com.unbounded.realizingself.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun convertTo24HourFormat(time12Hour: String): String {
    if (time12Hour.isBlank()) {
        // Handle empty or blank input string
        return "00:00" // or throw an exception, depending on your requirements
    }

    try {
        // Define the input and output formats
        val inputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Parse the input time
        val parsedTime = inputFormat.parse(time12Hour)

        // Format the parsed time to 24-hour format
        return outputFormat.format(parsedTime)
    } catch (e: ParseException) {
        // Handle parsing exceptions (e.g., invalid format)
        e.printStackTrace()
        return "00:00" // or throw an exception, depending on your requirements
    }
}
fun convertTo12HourFormat(time24Hour: String): String {
    if (time24Hour.isBlank()) {
        // Handle empty or blank input string
        return "12:00 AM" // or throw an exception, depending on your requirements
    }

    try {
        // Define the input and output formats
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
       // outputFormat.toUpperCase()

        // Parse the input time
        val parsedTime = inputFormat.parse(time24Hour)

        // Format the parsed time to 12-hour format
        return outputFormat.format(parsedTime).uppercase()
    } catch (e: ParseException) {
        // Handle parsing exceptions (e.g., invalid format)
        e.printStackTrace()
        return "12:00 AM" // or throw an exception, depending on your requirements
    }
}