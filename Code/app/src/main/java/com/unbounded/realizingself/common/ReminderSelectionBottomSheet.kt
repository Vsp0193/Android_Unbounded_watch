

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unbounded.realizingself.R
import kotlinx.coroutines.launch

/*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderSelectionBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    itemClickedData: MutableList<AllReminder>,
    viewModel: SettingViewModel,
    context: Context,
    onCancel: () -> Unit,
    onSave: (
        reminderDescription: String,
        time: String,
        frequency: List<Days>,
        token: String,
            ) -> Unit,
): MutableState<Boolean> {


    val days = listOf(
        Days("S", "0"),
        Days("M", "1"),
        Days("T", "2"),
        Days("W", "3"),
        Days("TH", "4"),
        Days("F", "5"),
        Days("SA", "6")
    )

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,

        )

    var selectedDaysList = remember { mutableStateListOf<Days>() }


    var reminderNameState by remember { mutableStateOf("") }
    var reminderTimeState by remember { mutableStateOf("") }
    val dataStoreViewModel: DataViewModel = hiltViewModel()


    if (itemClickedData.isNotEmpty()) {
        reminderNameState = itemClickedData[0].description
        reminderTimeState = itemClickedData[0].time

        itemClickedData[0].frequency.forEach { freq ->
            val day = days.find { it.frequency == freq }
            day?.let { selectedDaysList.add(it) }
        }

    }
    Log.d("TAG", "ReminderSelectionBottomSheet:$selectedDaysList ")

    var showTimePickerDialogDialog by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(getCurrentTime()) }
    if (showTimePickerDialogDialog) {
        CustomTimePickerDialog1(
            label = "Time Picker",
            onDismissRequest = {

                showTimePickerDialogDialog = false
            },
            onTimeSelected = { newTime ->
                selectedTime = newTime
                showTimePickerDialogDialog = false
                // Handle the selected time
            },
            selectedTime = selectedTime
        )
    }

    var focused by remember { mutableStateOf(false) }
    val windowInfo = LocalWindowInfo.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(windowInfo) {
        snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
            if (isWindowFocused) {
                focusRequester.requestFocus()
            }
        }
    }
    // Callback to intercept back button press
    ModalBottomSheet(
        onDismissRequest = {

            showBottomSheet.value = false
            onCancel()
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        shape = RoundedCornerShape(
            topStart = CornerSize(10.dp),
            topEnd = CornerSize(10.dp),
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        ),
        containerColor = Purple5A,
        modifier = Modifier
            .fillMaxHeight(0.6f),


        ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        BackHandler() {
            // Check if the keyboard is visible and the bottom sheet is not focused
            coroutineScope.launch {
                modalBottomSheetState!!.show()
            }

            keyboardController?.hide()

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(), // Minimum height set to 200.dp
            contentAlignment = Alignment.TopStart,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    TextField(
                        value = reminderNameState,
                        onValueChange = { newEmailState: String ->
                            reminderNameState = if (newEmailState.length <= 50) {
                                newEmailState
                            } else {
                                keyboardController?.hide()
                                newEmailState.take(50)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( horizontal = 10.dp)
                            .onFocusChanged {
                                focused = it.isFocused
                            }
                            .focusRequester(
                                focusRequester
                            ),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Start,
                            color = titleColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = titleColor,
                            backgroundColor = primary,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = red
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(size = 10.dp),
                        visualTransformation = VisualTransformation.None, // or any transformation you need
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focused = true
                                coroutineScope.launch {
                                    focusManager.clearFocus(true)
                                    keyboardController?.hide()
                                }
                                //context.hideKeyboard()
                            }),

                        interactionSource = remember { MutableInteractionSource() },


                        // verticalAlignment = Alignment.CenterVertically // Align content vertically
                    )
                    if (reminderNameState.isEmpty()) {
                        androidx.compose.material.Text(
                            text = "Reminder Name", style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                fontWeight = FontWeight(400),
                                color = gray
                            ), textAlign = TextAlign.Start,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 30.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_time),
                        contentDescription = null,
                        tint = cyanColor
                    )
                    androidx.compose.material.Text(
                        "Time",
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = titleColor,
                        ),
                        modifier = Modifier
                            .weight(1f) // Make the text occupy all available space
                            .padding(start = 12.dp)
                    )


                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = SecondaryLite,
                                shape = RoundedCornerShape(size = 5.dp)

                            )
                            .padding(vertical = 5.dp, horizontal = 5.dp)

                            .clickable {


                                showTimePickerDialogDialog = true
                            },


                        ) {
                        Text(
                            text = selectedTime,
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 49.sp,
                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                fontWeight = FontWeight(400),
                                color = white,
                            ),
                            modifier = Modifier.padding(
                                vertical = 3.dp,
                                horizontal = 10.dp
                            ) // Add padding for better readability
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        //  imageVector = ImageVector.vectorResource(id = R.drawable.icon_days),
                        painter = painterResource(id = R.drawable.icon_days),
                        contentDescription = null,
                        Modifier.size(21.dp),
                        tint = cyanColor
                    )
                    androidx.compose.material.Text(
                        "Days",
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = titleColor,
                        ),
                        modifier = Modifier
                            .weight(1f) // Make the text occupy all available space
                            .padding(start = 12.dp)
                    )


                }


                DaysList(
                    selectedDays = selectedDaysList,
                    onSelectionChanged = {
                        selectedDaysList = it.toMutableStateList()
                    }

                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(17.dp)
                )
                Button(
                    onClick = {
                        if (AppUtility.isNetworkAvailable(context)) {
                            if (reminderNameState.isNotEmpty()) {

                                onSave(
                                    reminderNameState,
                                    convertTo24HourFormat(selectedTime),
                                    selectedDaysList,
                                    dataStoreViewModel.getToken()!!
                                )
                                coroutineScope.launch {
                                    showBottomSheet.value = false
                                    modalBottomSheetState.hide()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    R.string.please_enter_reminder_name,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please check your internet connection",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }


                    },
                    Modifier
                        .shadow(
                            elevation = 44.dp,
                            spotColor = Color(0x331B4FAD),
                            ambientColor = Color(0x331B4FAD)
                        )
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 10.dp),
                    //enabled = emailState.isValid && passwordState.isValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        disabledContainerColor = secondary
                    )
                ) {
                    androidx.compose.material.Text(
                        text = "SAVE",

                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),

                            textAlign = TextAlign.Center,
                        )
                    )


                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(17.dp)
                )
                ClickableText(
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 49.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans_medium)),
                        fontWeight = FontWeight(400),
                        color = cyanColor,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(17.dp),
                    text = buildAnnotatedString {
                        append("CANCEL")
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                // textDecoration = TextDecoration.Underline, // No underline
                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                fontWeight = FontWeight(500),
                                color = cyanColor,
                            ),
                        ) {

                        }


                    }, onClick = {
                        onCancel()
                        coroutineScope.launch {
                            showBottomSheet.value = false
                            modalBottomSheetState.hide()
                        }
                    })

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(37.dp)
                )
            }
        }
    }
    return showBottomSheet
}


@Composable
fun DaysList(
    selectedDays: MutableList<Days>, // Changed to MutableStateList
    onSelectionChanged: (MutableList<Days>) -> Unit,
) {
    val days = listOf(
        Days("S", "0"),
        Days("M", "1"),
        Days("T", "2"),
        Days("W", "3"),
        Days("TH", "4"),
        Days("F", "5"),
        Days("SA", "6")
    )

    // Ensure all items are initially selected
    LaunchedEffect(Unit) {
        selectedDays.addAll(days)
    }

    LazyRow(
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(days.size) { day ->
            val isSelected = selectedDays.contains(days[day])

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = if (isSelected) Purple else primary,
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .size(42.dp)
                    .clickable {
                        if (isSelected) {
                            selectedDays.remove(days[day])
                        } else {
                            selectedDays.add(days[day])
                        }
                        onSelectionChanged(selectedDays)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = days[day].day,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 49.sp,
                        fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) return currentContext
        currentContext = currentContext.baseContext
    }
    return null
}

fun Context.hideKeyboard() {
    findActivity()?.let { activity ->
        val view = activity.currentFocus ?: View(activity)
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
*/
