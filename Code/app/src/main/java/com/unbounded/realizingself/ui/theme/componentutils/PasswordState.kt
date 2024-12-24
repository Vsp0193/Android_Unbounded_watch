package com.peak.unbounded.features.ui.componentutils


class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    val pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$".toRegex()
    return pattern.matches(password)

}


@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Password should be at least 8 characters and must contain uppercase, lowercase, special character, and number."
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}
private fun isUserFNameValid(name: String): Boolean {
    return name.isNotEmpty()
}
private fun userFNameError(name: String): String {
    return "Please enter first name."
}
class UserFNameState :
    TextFieldState(validator = ::isUserFNameValid, errorFor = ::userFNameError)
private fun isUserLNameValid(name: String): Boolean {
    return name.isNotEmpty()
}
private fun userLNameError(name: String): String {
    return "Please enter last name."
}
class UserLNameState :
    TextFieldState(validator = ::isUserLNameValid, errorFor = ::userLNameError)