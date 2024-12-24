

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.unbounded.realizingself.R
import com.unbounded.realizingself.common.getCurrentTime
import com.unbounded.realizingself.ui.theme.SecondaryLite
import com.unbounded.realizingself.ui.theme.black
import com.unbounded.realizingself.ui.theme.black_alpha
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.titleColor
import com.unbounded.realizingself.ui.theme.white

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Define a callback function type to handle the selected time
typealias OnTimeSelected = (String) -> Unit

@Composable
fun CustomTimePickerDialog1(
    label: String,
    onDismissRequest: () -> Unit,
    onTimeSelected: OnTimeSelected, // Callback to handle the selected time
    selectedTime: String, // Selected time
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        TimePickerUI(label, onDismissRequest, onTimeSelected, selectedTime)
    }
}

@Composable
fun TimePickerUI(
    label: String,
    onDismissRequest: () -> Unit,
    onTimeSelected: OnTimeSelected, // Callback to handle the selected time
    selectedTime: String, // Selected time
) {
    // Get the current time in HH:mm format
    val currentTime = getCurrentTime()
    val timeParts = currentTime.split(" ")
    val hourMinuteParts = timeParts[0].split(":")
    val currentHour = hourMinuteParts[0]
    val currentMinute = hourMinuteParts[1]
    val currentAmPm = timeParts[1]
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))


            val chosenHour = remember { mutableStateOf(currentHour) }
            val chosenMinute = remember { mutableStateOf(currentMinute) }
            val chosenAmPm = remember { mutableStateOf(currentAmPm) }

            // Time selection section
            TimeSelectionSection(
                selectedHour = chosenHour.value,
                selectedMinute = chosenMinute.value,
                selectedAMPM = chosenAmPm.value,
                onHourChosen = { chosenHour.value = it },
                onMinuteChosen = { chosenMinute.value = it }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // AM/PM buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { chosenAmPm.value = "AM" },
                    shape = ButtonDefaults.shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (chosenAmPm.value == "AM") primary else SecondaryLite,
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "AM",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(500),
                            color = titleColor,
                        ),
                    )
                }

                Button(
                    onClick = { chosenAmPm.value = "PM" },
                    shape = ButtonDefaults.shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (chosenAmPm.value == "PM") primary else SecondaryLite,
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "PM",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(500),
                            color = titleColor,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val context = LocalContext.current
            Button(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onClick = {
                    // Construct the selected time with hour, minute, and AM/PM parts
                    val selectedTime =
                        "${chosenHour.value}:${chosenMinute.value} ${chosenAmPm.value}"
                    // Pass the selected time to the callback function
                    onTimeSelected(selectedTime)
                    onDismissRequest()
                }
            ) {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}




@Composable
fun TimeSelectionSection(
    selectedHour: String,
    selectedMinute: String,
    selectedAMPM: String,
    onHourChosen: (String) -> Unit,
    onMinuteChosen: (String) -> Unit,
) {
   // val hours = (1..12).map { it.toString().padStart(2, '0') }
   // val minutes = (0..59).map { it.toString().padStart(2, '0') }

    val currentHour =  if (selectedHour=="0" ||selectedHour=="00") {
        myHours.indexOf("12")
    }
      else  if (selectedHour.isNotEmpty()) {
        myHours.indexOf(selectedHour)
    } else {
        // Set a default hour, e.g., the first item in the hours list
        0
    }

    val currentMinute = if (selectedMinute=="0" ||selectedMinute=="00") {
        myMinutes.indexOf("00")
    }
      else  if (selectedMinute.isNotEmpty()) {
        myMinutes.indexOf(selectedMinute)
    } else {
        // Set a default minute, e.g., the first item in the minutes list
        0
    }

    Log.d("Time_Selected", "selectedHour:$selectedHour ")
    Log.d("Time_Selected", "selectedMinute:$selectedMinute ")
    Log.d("Time_Selected", "currentHour:$currentHour ")
    Log.d("Time_Selected", "currentMinute:$currentMinute ")
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            InfiniteItemsPickerLogMeditation(
                items = myHours,
                firstIndex = if (currentHour!=0) currentHour-1 else 0,
                onItemSelected = onHourChosen
            )

            Text(
                text = ":",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 40.dp)
            )

            InfiniteItemsPickerLogMeditation(
                items = myMinutes,
                firstIndex =  if (currentMinute!=0) currentMinute-1 else 0,
                onItemSelected = onMinuteChosen
            )
        }

}


@Composable
fun InfiniteItemsPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    firstIndex: Int,
    onItemSelected: (String) -> Unit,
) {
    val listState = rememberLazyListState(firstIndex)

    Box(modifier = Modifier.height(106.dp)) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            content = {
                items(count = Int.MAX_VALUE, itemContent = {
                    val index = it % items.size
                    if (it == listState.firstVisibleItemIndex + 1) {
                        onItemSelected(items[index])
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    val alphaValue = if (index == listState.firstVisibleItemIndex + 1) 1f else 0.3f
                    val validAlphaValue = alphaValue.coerceIn(0f, 1f)
                    Text(
                        text = items[index],
                        color = white.copy(validAlphaValue),
                        modifier = Modifier
                        .alpha(validAlphaValue),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                })
            }
        )
    }
}


@Composable
fun InfiniteItemsPickerLogMeditation(
    modifier: Modifier = Modifier,
    items: List<String>,
    firstIndex: Int,
    onItemSelected: (String) -> Unit,
) {
    val listState = rememberLazyListState(firstIndex)
    Log.d("TimePicker", "comming: ")
    Log.d("Time_Selected", "firstIndex:${firstIndex}")
    Box(modifier = modifier.height(106.dp)) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            content = {
                items(count = Int.MAX_VALUE) {
                    val index = it % items.size
                    val isSelected = it == listState.firstVisibleItemIndex + 1

                    if (isSelected) {
                        onItemSelected(items[index])
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                   // val alphaValue = if (isSelected) 1f else 0.3f
                    //val validAlphaValue = alphaValue.coerceIn(0f, 1f)
                    val color =  if (isSelected) black else black_alpha
                    Text(
                        text = items[index],
                        color =color, // Ensure alpha is in the valid range
                        modifier = Modifier
                            //.alpha(validAlphaValue)
                        ,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        )
    }
}


// Function to get the current time in HH:mm format
private fun getCurrentSystemTimeTime(): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(currentTime)
}


val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

val hours = (1..12).map { it.toString().padStart(2, '0') }
val minutes = (0..59).map { it.toString().padStart(2, '0') }
val minutesforLogMeditation = (1..30).map { it.toString().padStart(2, '0') }
val myHours= listOf<String>( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
val myMinutes= listOf<String>("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59")





