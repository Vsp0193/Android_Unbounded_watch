package com.peak.unbounded.features.ui.componentutils

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.peak.unbounded.R
import com.peak.unbounded.features.ui.theme.TintEyeColor
import com.peak.unbounded.features.ui.theme.cyanColor
import com.peak.unbounded.features.ui.theme.red

class UiUtils {
companion object {
    @Composable
    fun NewPassword(
        passState: TextFieldState = remember { PasswordState() },
        onImeAction: () -> Unit = {},
    ) {
        var showPassword by remember { mutableStateOf(false) }

        TextField(
            value = passState.text,
            onValueChange = { passState.text = it
                if (it.length>7 &&!passState.isValid){
                    passState.enableShowErrors()
                }
                    },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(
                    "Password",
                    color = Color.White
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) {
                            ImageVector.vectorResource(id = R.drawable.eye)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.hide)
                        },
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = TintEyeColor
                    )
                }
            },
            modifier = Modifier
                .onFocusChanged { focusState ->
                    passState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        passState.enableShowErrors()
                    }
                }
                .fillMaxWidth()
                .height(57.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = passState.showErrors(),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = cyanColor,
                unfocusedIndicatorColor = cyanColor,
                errorIndicatorColor = red
            )
        )

        passState.getError()?.let { error ->
            TextFieldError(textError = error)
        }
    }
    @Composable
    fun ChangePassword(
        passState: TextFieldState = remember { PasswordState() },
        title: String,
        focusRequester: FocusRequester, // Receive focusRequester as a parameter
        onImeAction: () -> Unit = {},
    ) {
        var showPassword by remember { mutableStateOf(false) }

        TextField(
            value = passState.text,
            onValueChange = {
                passState.text = it
                if (it.length > 7 && !passState.isValid) {
                    passState.enableShowErrors()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(
                    title,
                    color = Color.White
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) {
                            ImageVector.vectorResource(id = R.drawable.eye)
                        } else {
                            ImageVector.vectorResource(id = R.drawable.hide)
                        },
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        tint = TintEyeColor
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .focusRequester(focusRequester), // Apply focusRequester modifier
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = passState.showErrors(),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = cyanColor,
                unfocusedIndicatorColor = cyanColor,
                errorIndicatorColor = red
            )
        )

        passState.getError()?.let { error ->
            TextFieldError(textError = error)
        }
    }


    @Composable
    fun Email(
        readOnly:Boolean,
        enable:Boolean,
        emailState: TextFieldState = remember { EmailState() },
        imeAction: ImeAction = ImeAction.Next,
        placeHolder:String,
        onImeAction: () -> Unit = {},
    ) {

        val hasInteracted = remember { mutableStateOf(false) }
        TextField(
            value = emailState.text,

            onValueChange = {
                emailState.text = it
            },
            label = {
                Text(
                    placeHolder,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor =cyanColor,
                unfocusedIndicatorColor =cyanColor,
                errorIndicatorColor = red
            ),
            modifier = Modifier
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        hasInteracted.value = true
                    }
                    emailState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        emailState.enableShowErrors()
                    }
                }
                .fillMaxWidth(),

            textStyle = MaterialTheme.typography.bodyMedium,
            isError = hasInteracted.value && emailState.showErrors(),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            readOnly = readOnly,
            enabled = enable,
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
        )
        if (hasInteracted.value) {
            emailState.getError()?.let { error ->
                TextFieldError(textError = error)
            }
        }
    }
    @Composable
    fun UserFName(
        readOnly:Boolean,
        enable:Boolean,
        label:String,
        usernameState: TextFieldState = remember { UserFNameState() },
        imeAction: ImeAction = ImeAction.Next,
        onImeAction: () -> Unit = {}
    ) {
        TextField(
            value = usernameState.text,
            onValueChange = {
                Log.d("UserFName", "UserFName:${  usernameState.text} ")
                Log.d("UserFName", "UserFName:${  it} ")
                usernameState.text = it
                            },
            label = {
                Text(
                    label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = cyanColor,
                unfocusedIndicatorColor = cyanColor,
                errorIndicatorColor = red
            ),
            modifier = Modifier
                .onFocusChanged { focusState ->
                    usernameState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused && usernameState.text.isEmpty()) {
                        usernameState.enableShowErrors()
                    }
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = usernameState.showErrors(),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            readOnly = readOnly,
            enabled = enable,
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
        )

        usernameState.getError()?.let { error -> TextFieldError(textError = error) }
    }
    @Composable
    fun UserLName(
        readOnly:Boolean,
        enable:Boolean,
        label:String,
        usernameState: TextFieldState = remember { UserLNameState() },
        imeAction: ImeAction = ImeAction.Next,
        onImeAction: () -> Unit = {}
    ) {
        TextField(
            value = usernameState.text,
            onValueChange = {it->
                usernameState.text = it
                            },
            label = {
                Text(
                    label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = cyanColor,
                unfocusedIndicatorColor = cyanColor,
                errorIndicatorColor = red
            ),
            modifier = Modifier
                .onFocusChanged { focusState ->
                    usernameState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused && usernameState.text.isEmpty()) {
                        usernameState.enableShowErrors()
                    }
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = usernameState.showErrors(),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),

            readOnly = readOnly,
            enabled = enable,
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
        )

        usernameState.getError()?.let { error -> TextFieldError(textError = error) }
    }
    @Composable
    fun OtpField(
        label:String,
        usernameState: TextFieldState = remember { UserLNameState() },
        imeAction: ImeAction = ImeAction.Next,
        onImeAction: () -> Unit = {}
    ) {
        TextField(
            value = usernameState.text,
            onValueChange = { usernameState.text = it

                            },
            label = {
                Text(
                    label,
                    color = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = cyanColor,
                unfocusedIndicatorColor = cyanColor,
                errorIndicatorColor = red
            ),
            modifier = Modifier
                .onFocusChanged { focusState ->
                    usernameState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused && usernameState.text.isEmpty()) {
                        usernameState.enableShowErrors()
                    }
                }
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            isError = usernameState.showErrors(),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            singleLine = true,
            shape = RoundedCornerShape(size = 80.dp),
        )

        usernameState.getError()?.let { error -> TextFieldError(textError = error) }
    }

}
}