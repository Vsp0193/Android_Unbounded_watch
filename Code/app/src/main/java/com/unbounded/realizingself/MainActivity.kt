package com.unbounded.realizingself

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.TimeTextDefaults.TimeFormat12Hours
import com.peak.unbounded.features.data.local.DataViewModel
import com.unbounded.realizingself.NAVIGATION.OnBoardingGraph
import com.unbounded.realizingself.NAVIGATION.ScreenNavigationGraph
import com.unbounded.realizingself.common.SpinningProgressBar
import com.unbounded.realizingself.ui.theme.RealizingSelfTheme
import com.unbounded.realizingself.ui.theme.black
import com.unbounded.realizingself.ui.theme.blue
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.secondary
import com.unbounded.realizingself.ui.theme.transparant
import com.unbounded.realizingself.ui.theme.white
import com.unbounded.realizingself.utils.AppUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealizingSelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenNavigationGraph(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineListScreen(
    dataTransferViewModel: DataTransferViewModel,
    navHostController: NavHostController,

) {
    val context = LocalContext.current
    val manageUser = true
   val token =
      if (manageUser){
          "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTcxMCwiaWF0IjoxNzM1MjgxNDI5fQ.UFZJNMJDqLKCu24qEnfBVA97M30lDao7pjVFAlBGv88"
      }else {
          "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUzMiwiaWF0IjoxNzM1NTQ5MjQ1fQ.-oQzcYFTomu5y25G90-yDDqEUAw8X9AaSwicrZb1EnY"
      }
    val userID =  if (manageUser){
        1710
    }else
        1532
    val scope = rememberCoroutineScope()
    val viewModel: MeditationRoutineViewModel = hiltViewModel()


    val dataStoreViewModel: DataViewModel = hiltViewModel()

    val userMeditationRoutineListApiData = remember {
        viewModel.responseData
    }
    // Observe the API data from the ViewModel

    // ScalingLazyColumn's state
    val listState = rememberScalingLazyListState()
    val userMeditationRoutineList =
        userMeditationRoutineListApiData.value?.userPresets?.toMutableList() ?: mutableListOf()

    val loadError = remember {
        viewModel.loadError
    }
    val isLoading = remember {
        viewModel.isLoading
    }
    val dataMessage = remember {
        viewModel.dataMessage
    }
 /*   LaunchedEffect(timeSegmentResponseDataApiData.value) {

        withContext(Dispatchers.Main) {
            Log.d("callfowedfwodw", "RoutineListScreen: callfowedfwodw")
            navHostController.navigate("${OnBoardingGraph.StartScreen.route}/$timeSegmentResponseDataApiData.value")

        }
    }
*/

    LaunchedEffect(userMeditationRoutineListApiData.value) {
        withContext(Dispatchers.Main) {
            if (AppUtility.isNetworkAvailable(context)) {
                isLoading.value = true
                viewModel.getMeditationRoutineList(
                    token = token,
                    userId = userID
                )
            } else {

                Toast.makeText(
                    context,
                    "Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(black),

    ) {
        Column (

        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        black
                    )
                    .weight(.6f) ,
                contentAlignment = Alignment.TopCenter
            ) {
                val isRound = context.resources.configuration.isScreenRound()
                Log.d("isRound", "RoutineListScreen: $isRound")
                if (isRound) {
                    // For round screens: hello_world in the center, TimeText below it
                    Column(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = white,
                            text = "Unbounded",
                            fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),
                        )
                        TimeText(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                } else {
                    // For non-round screens: hello_world in the top-left, TimeText in the top-right
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(start = 3.dp, end = 3.dp, top = 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Start,
                            color = white,
                            text = "Unbounded",
                            fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),


                            )

                        TimeText(
                            modifier = Modifier.padding(start = 40.dp),
                            timeTextStyle = TimeTextDefaults.timeTextStyle(
                               // formatter = TimeFormat12Hours,
                                color = Color.White,
                                fontSize = 14.sp,
                               // fontFamily = FontFamily(Font(R.font.dm_sans_semibold))
                            ),

                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp), // Optional padding

                        )
                    }
                }
            }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .background(
                    black
                ),
            contentAlignment = Alignment.TopStart
        ) {
            // ScalingLazyColumn to display the data
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        black
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading.value) {
                    SpinningProgressBar(
                        modifier = Modifier.size(32.dp)
                    )
                }
                if (loadError.value.isNotEmpty()) {
                    Toast.makeText(context, loadError.value, Toast.LENGTH_SHORT).show()
                    loadError.value = ""
                }
                if (dataMessage.value.isNotEmpty()) {
                    Toast.makeText(context, dataMessage.value, Toast.LENGTH_SHORT).show()
                    dataMessage.value = ""
                }
            }
            ScalingLazyColumn(
                state = listState,
                modifier = Modifier.align(alignment = Alignment.TopStart)
            ) {
                if (userMeditationRoutineList.isEmpty()) {
                    // Show a placeholder if the list is empty
                    item {
                        Text(
                            text = "No meditation routines available",
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 49.sp,
                                fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                } else {
                    // Dynamically render items from the API response
                    items(userMeditationRoutineList.size) { index ->
                        val item = userMeditationRoutineList[index]

                        val parts = item.presetSegmentDescription.split(", ")

                        // Extract data for the first part
                        val firstPart = parts[0].split(" ", limit = 3)
                        val firstVar = firstPart[0].toInt() // Extract number
                        val firstUnit = firstPart[1]       // Extract "minutes"
                        val firstActivity = firstPart[2]  // Extract "Meditation"

                        // Extract data for the second part
                        val secondPart = parts[1].split(" ", limit = 3)
                        val secondVar = secondPart[0].toInt() // Extract number
                        val secondUnit = secondPart[1]        // Extract "minutes"
                        val secondActivity = secondPart[2]
                        Chip(
                            onClick = {
                               // navHostController.navigate("${OnBoardingGraph.StartScreen.route}/$item")

                                    if (AppUtility.isNetworkAvailable(context)) {
                                        isLoading.value = true
                                        Log.d("checkApicallingInRoutine", "-->active")
                                        viewModel.activeMeditationRoutineList(
                                             dataTransferViewModel,
                                            navHostController,
                                            token = token,
                                            presetId = item.id,
                                            userID
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please check your internet connection",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            },
                            colors = ChipDefaults.chipColors(primary),
                            shape = RoundedCornerShape(12.dp),// Handle the click event
                            modifier = Modifier
                                .background(transparant)
                                .fillMaxWidth(),

                            leadingIcon = {
                                // Add an optional icon if needed (e.g., an avatar or status icon)
                            },
                            content = {
                                // Use RowScope to define the content
                                Column(modifier = Modifier
                                    .background(primary)
                                    .padding(horizontal = 5.dp, vertical = 6.dp),
                                    Arrangement.Top) {
                                  //  Text(text = item.description) // Main label
                                   Text(
                                        text = item.description,
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 49.sp,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                            textAlign = TextAlign.Center,
                                        )
                                    )
                                    Row {

                                        Text(
                                            text ="${firstVar}m ",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = blue,
                                                textAlign = TextAlign.Center,
                                            )
                                        )

                                        Text(
                                            text ="$firstActivity",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                            )
                                        )
                                    }
                                    Row {

                                        Text(
                                            text ="${secondVar}m ",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = blue,
                                                textAlign = TextAlign.Center,
                                            )
                                        )

                                        Text(
                                            text ="$secondActivity",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                            )
                                        )
                                    }

                                }
                            }
                        )

                    }
                }
            }

        }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RealizingSelfTheme {
        //Greeting("Android")
    }
}
