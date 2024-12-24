package com.peak.unbounded.features.ui.componentutils

import androidx.compose.foundation.ExperimentalFoundationApi

import java.util.regex.Pattern


// Consider an email valid if there's some text before and after a "@"
private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\\.com\$"

@OptIn(ExperimentalFoundationApi::class)
class EmailState(val email: String? = null) :
    TextFieldState(validator = ::isEmailValid, errorFor = ::emailValidationError) {
    init {
        email?.let {
            text = it
        }
    }
}

/**
 * Returns an error to be displayed or null if no error was found
 */
private fun emailValidationError(email: String): String {
 //   return "Please enter a valid email: $email"
    return "Please enter a valid email"
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}

val EmailStateSaver = textFieldStateSaver(EmailState())

