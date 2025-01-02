package com.unbounded.realizingself.utils

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AppUtility {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

    fun getCurrentDateTimeInISOFormat(): String {
        // Define the desired date format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        // Set the time zone to UTC
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        // Get the current date and time
        val currentDate = Date()
        // Format the date and time
        return dateFormat.format(currentDate)
    }
    }
}