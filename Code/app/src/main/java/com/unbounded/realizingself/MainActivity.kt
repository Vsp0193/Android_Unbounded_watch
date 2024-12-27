package com.unbounded.realizingself

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Chip
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
import androidx.compose.ui.graphics.Brush
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
import com.peak.unbounded.features.data.local.DataViewModel
import com.unbounded.realizingself.NAVIGATION.OnBoardingGraph
import com.unbounded.realizingself.NAVIGATION.ScreenNavigationGraph
import com.unbounded.realizingself.ui.theme.RealizingSelfTheme
import com.unbounded.realizingself.ui.theme.blue
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.secondary
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
    // Allow a modifier to be passed
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    val token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTcxMCwiaWF0IjoxNzM1MjgxNDI5fQ.UFZJNMJDqLKCu24qEnfBVA97M30lDao7pjVFAlBGv88"
    val id = 1710
    val scope = rememberCoroutineScope()
    val viewModel: MeditationRoutineViewModel = hiltViewModel()
    val timeSegmentResponseDataApiData = remember {
        viewModel.timeSegmentResponseData
    }
    val dataStoreViewModel: DataViewModel = hiltViewModel()
    val timeSegmentResponseData =
        timeSegmentResponseDataApiData.value?.userTimeSegments?.toMutableList() ?: mutableListOf()

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
    LaunchedEffect(timeSegmentResponseDataApiData.value) {

        withContext(Dispatchers.Main) {
            navHostController.navigate("${OnBoardingGraph.StartScreen.route}/$timeSegmentResponseDataApiData.value")

        }
    }


    LaunchedEffect(userMeditationRoutineListApiData.value) {
        withContext(Dispatchers.Main) {
            if (AppUtility.isNetworkAvailable(context)) {
                isLoading.value = true
                viewModel.getMeditationRoutineList(
                    token = token,
                    userId = id
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
            .fillMaxSize()
            .background(blue),
        contentAlignment = Alignment.Center
    ) {
        val isRound = context.resources.configuration.isScreenRound()
        if (isRound) {
            // For round screens: hello_world in the center, TimeText below it
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = primary,
                    text = "hello_world"
                )
                TimeText(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            // For non-round screens: hello_world in the top-left, TimeText in the top-right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    textAlign = TextAlign.Start,
                    color = primary,
                    text = "hello_world"
                )
                TimeText(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primary, secondary)
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            // ScalingLazyColumn to display the data
            ScalingLazyColumn(
                state = listState,
                contentPadding = PaddingValues(16.dp)
            ) {
                if (userMeditationRoutineList.isEmpty()) {
                    // Show a placeholder if the list is empty
                    item {
                        Text(text = "No meditation routines available")
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
                                            dataStoreViewModel = dataStoreViewModel,
                                            token = token,
                                            presetId = item.id
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please check your internet connection",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }



                            }, // Handle the click event
                            modifier = Modifier.background(blue),
                            leadingIcon = {
                                // Add an optional icon if needed (e.g., an avatar or status icon)
                            },
                            content = {
                                // Use RowScope to define the content
                                Column {
                                  //  Text(text = item.description) // Main label
                                   Text(
                                        text = item.description,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 49.sp,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                            textAlign = TextAlign.Center,
                                        )
                                    )
                                    Row {

                                        Text(
                                            text ="$firstVar",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                            )
                                        )

                                        Text(
                                            text ="$firstActivity",
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
                                    Row {

                                        Text(
                                            text ="$secondVar",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 49.sp,
                                                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                                fontWeight = FontWeight(400),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                            )
                                        )

                                        Text(
                                            text ="$secondActivity",
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

                                }
                            }
                        )

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
