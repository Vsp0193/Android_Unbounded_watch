package com.unbounded.realizingself.component

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.peak.unbounded.features.data.local.DataViewModel
import com.unbounded.realizingself.ui.theme.TintEyeColor
import com.unbounded.realizingself.ui.theme.green97
import com.unbounded.realizingself.ui.theme.primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.compareTo
import kotlin.math.cos
import kotlin.math.sin
import com.unbounded.realizingself.R
import com.unbounded.realizingself.model.segment.UserPreset
import com.unbounded.realizingself.ui.theme.cyanColor

/*
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@Composable
fun SegmentedProgressBar(viewModel: UserPreset, navController: NavHostController) {


    var nextSagmentName = ""

    Log.d("Timee_screen", "SegmentedProgressBar:2 ")
    val isBottomNavigationVisible by viewModel.isBottomNavigationVisible.observeAsState(true)
    val elapsedTime = viewModel.elapsedTime.value
    val actualMeditationTime = viewModel.actualMeditationTime.value
    val totalDuration = viewModel.totalDuration.value.toFloat()
    val strokeWidth = 24f
    val radius = 150.dp
    val gapDegrees = 3f  // Degrees of gap between segments
    val dashArray = floatArrayOf(8f, 8f)  // Dash pattern
    val segmentDataApiCall = remember {
        viewModel.segmentDataApiCall
    }

    var apiCallMade by remember { mutableStateOf(false) }
    val viewModel: TimerScreenViewModel = hiltViewModel()
    val dataStoreViewModel: DataViewModel = hiltViewModel()
    val isFreshView by viewModel.isFreshView.observeAsState(true)
    val isProgressCompleted by viewModel.isProgressCompleted.observeAsState(false)
    val isMilestoneApiDone by viewModel.isMilestoneApiDone.observeAsState(false)

    var isApiCalled by remember { mutableStateOf(false) }
    */
/*  var isProgressCompleted by remember { mutableStateOf(false) }
      LaunchedEffect(key1 = ProgressCompleted) {
          isProgressCompleted = ProgressCompleted
      }*//*


    var currentIndex = viewModel.currentIndex
    var isFloatingTextVisible by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val segmentInfo = viewModel.segmentInfoLivedata.observeAsState()
    var segmentList by remember { mutableStateOf(viewModel.segmentList) }
    val isProgressStarted by viewModel.isProgressStarted.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    val isProgressPaused by viewModel.isProgressPaused.observeAsState(false)
    val currentSegmentName by viewModel.currentSegmentName.observeAsState("")
    val nextSegmentName by viewModel.nextSegmentName.observeAsState("")
    viewModel.initContext(context) // Pass the context of your activity or fragment
    val animationProgress by viewModel.animationProgress.observeAsState(0f)
    val segmentCompletion = remember { mutableStateListOf<Boolean>() }
    var isTimerRunning by remember { mutableStateOf(false) }
    val timeRemaining by viewModel.timeRemaining.observeAsState(0)

    var formattedTime = remember(timeRemaining) {
        String.format("%02d:%02d", timeRemaining / 60, timeRemaining % 60)
    }
    var isApiCalledSuccess = viewModel.isSuccess.value

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    repeat(segmentList.size) {
        coroutineScope.launch {
            segmentCompletion.add(false)
        }
    }


    val Error = remember {
        viewModel.loadError
    }
    var loadError = remember {
        mutableStateOf<String>("")
    }
    LaunchedEffect(key1 = Error) {
        loadError.value = Error.value
    }

    val isLoading = remember {
        viewModel.isLoading
    }
    val dataMessage = remember {
        viewModel.dataMessage
    }

    var mileStoneApiCallResponse =
        viewModel.mileStoneApiCallResponse
    var mileStoneApiCallResponseData = remember { mutableStateOf<CreateMeditationResponse?>(null) }
    var meditationId by remember { mutableIntStateOf( 0) }
    LaunchedEffect(mileStoneApiCallResponse.value) {
        mileStoneApiCallResponseData.value = null
        mileStoneApiCallResponseData.value = mileStoneApiCallResponse.value
        Log.d("apimilestorn", "mileStoneApiCallResponse: ${mileStoneApiCallResponse.value?.id?:0}")
        meditationId =   mileStoneApiCallResponse.value?.id?:0

        Log.d("apimilestorn", "actualMeditationTime: $meditationId")
    }



    var tapAnyWhereTextShow by remember { mutableStateOf(false) }


    val dndMode = remember { mutableStateOf(false) }
    val addNotesBottomSheetState = remember { mutableStateOf(false) }

    var quickTipsShow by remember { mutableStateOf(false) }
    val routineName = remember { mutableStateOf("") }

    if (dndMode.value) {
        openDoNotDisturbSettings()
    }


    if (addNotesBottomSheetState.value) {
        viewModel.loadError.value = ""
        viewModel.resetTimer()//
        coroutineScope.launch {
            viewModel.resetUpdate(true)
            viewModel.resetAll()// Reset all states and data
            viewModel.resetTimer()// Reset all states and data
            isTimerRunning = false
            segmentCompletion.clear() // Clear and reinitialize the segment completion list
            repeat(segmentList.size) {
                segmentCompletion.add(false)
            }
        }
        AddNotesBottomSheet(
            addNotesBottomSheetState,
            context = context,
            onCancel = {

            },
            onSave = {
                //  viewModel.isLoading.value = true

                coroutineScope.launch {
                    viewModel.resetTimer()
                    viewModel.resetAll()
                    isApiCalled =false
                    Log.d("apimilestorn", "actualMeditationTime: $meditationId")
                    if (meditationId != 0) {
                        viewModel.addMeditationNoteApiCall(
                            dataStoreViewModel,
                            isDelete = false,
                            token = dataStoreViewModel.getToken()!!,
                            notes = it,
                            meditationId = meditationId
                        )
                    }
                }


            }
        )
    }


    var userId = remember {
        mutableIntStateOf(0)

    }
    var token = remember {
        mutableStateOf<String?>(null)

    }

    LaunchedEffect(Unit) {
        while (true) {
            tapAnyWhereTextShow = false
            delay(10000)
        }
    }
    coroutineScope.launch {
        Log.d("manishddhdhddhddhd", "SegmentedProgressBar: ${dataStoreViewModel.getUserId()?:0}")
        Log.d("manishddhdhddhddhd", "SegmentedProgressBar: ${dataStoreViewModel.getToken()?:""}")
        isLoading.value = true
        userId.value = withContext(Dispatchers.IO) {
            dataStoreViewModel.getUserId()?:0
        }

        token.value = withContext(Dispatchers.IO) {
            dataStoreViewModel.getToken()
        }
    }


    LaunchedEffect(key1 = userId.value, key2 = token.value) {
        Log.d("edfwekqldwe", "SegmentedProgressBar: $userId")


        if (userId.value != null && token.value != null) {
            viewModel.segments.clear()
            viewModel.segmentDataApiCall(userId.value.toString(), token.value!!)
        }

        val isTimeElapsed = withContext(Dispatchers.IO) {
            dataStoreViewModel.getIsTimeElapsed()
        }

        viewModel._isReverseTimer.value = isTimeElapsed ?: false

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(primary, primary, secondary)
                )
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    coroutineScope.launch {

                        if (!isFreshView && !isProgressCompleted) {
                            viewModel.setProgressPaused()
                            viewModel.setIsFloatingTextVisible(true)
                            viewModel.pauseTimer()
                        }
                    }
                }),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //  .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 20.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.End
            ) {
                if (isBottomNavigationVisible) {
                    IconButton(modifier = Modifier, onClick = {
                        navController.navigate("MeditationRoutineScreen")
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.timer_settings),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

            }

            if (segmentDataApiCall.value?.userTimeSegments.isNullOrEmpty()) {
                Log.d("ffdkwdkwdwkdwmdwdmwdkwdw", "segmentDataApiCall: ${segmentDataApiCall.value?.userTimeSegments?.size}")
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 100.dp),
                    //  verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center // This arranges children horizontally in the center
                ) {
                    androidx.compose.material.Text(
                        text = "You have no time segments. Please go to settings",
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center, // Ensures text itself is centered if Text exceeds one line
                        fontFamily = FontFamily(Font(R.font.dm_sans_light)),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }

            } else {


                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom

                ) {
                    Box(modifier = Modifier.height(screenHeight / 2))
                    {
                        val coroutineScope = rememberCoroutineScope()
                        var isTimerRunning by remember { mutableStateOf(false) }
                        val segmentList = viewModel.segments.map { it.duration }
                        val segmentCompletion =
                            remember(segmentList) { mutableStateListOf(*Array(segmentList.size) { false }) }


                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 20.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.aspectRatio(1f)) {
                                var dotColor = Color.Gray
                                val Purple45 = Color(0xFF6200EA)

                                Canvas(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                ) {
                                    val innerRadius = size.minDimension / 2 - strokeWidth / 2
                                    var startAngle = 270f
                                    var cumulativeDuration = 0f
                                    var completedSegmentIndices = mutableListOf<Int>()

                                    if (isFreshView) {
                                        viewModel.segments.forEachIndexed { index, segment ->

                                            val segmentDuration = segment.duration.toFloat()
                                            val segmentEndTime =
                                                cumulativeDuration + segmentDuration
                                            val segmentProgress = when {
                                                elapsedTime >= segmentEndTime -> 1f
                                                elapsedTime > cumulativeDuration -> (elapsedTime - cumulativeDuration) / segmentDuration
                                                else -> 0f
                                            }

                                            val sweepAngle =
                                                360f * (segmentDuration / totalDuration)
                                            val progressAngle = segmentProgress * sweepAngle

                                            // Draw grey arc
                                            drawArc(
                                                color = cyanColor,
                                                startAngle = startAngle,
                                                sweepAngle = sweepAngle,
                                                useCenter = false,
                                                style = Stroke(
                                                    width = strokeWidth,
                                                    pathEffect = PathEffect.dashPathEffect(dashArray)
                                                )
                                            )

                                            cumulativeDuration += segmentDuration
                                            startAngle += sweepAngle
                                        }
                                    } else {
                                        Log.d("djsd", "SegmentedProgressBar:${ viewModel.segments.size} ")
                                        viewModel.segments.forEachIndexed { index, segment ->

                                            val segmentDuration = segment.duration.toFloat()
                                            val segmentEndTime =
                                                cumulativeDuration + segmentDuration
                                            val segmentProgress = when {
                                                elapsedTime >= segmentEndTime -> 1f
                                                elapsedTime > cumulativeDuration -> (elapsedTime - cumulativeDuration) / segmentDuration
                                                else -> 0f
                                            }

                                            val sweepAngle =
                                                360f * (segmentDuration / totalDuration) - .8f
                                            val progressAngle = segmentProgress * sweepAngle

                                            // Draw grey arc
                                            drawArc(
                                                color = Color.Gray,
                                                startAngle = startAngle,
                                                sweepAngle = sweepAngle,
                                                useCenter = false,
                                                style = Stroke(
                                                    width = strokeWidth,
                                                    pathEffect = PathEffect.dashPathEffect(dashArray)
                                                )
                                            )

                                            // Draw cyan arc
                                            drawArc(
                                                color = cyanColor,
                                                startAngle = startAngle,
                                                sweepAngle = progressAngle,
                                                useCenter = false,
                                                style = Stroke(
                                                    width = strokeWidth,
                                                    pathEffect = PathEffect.dashPathEffect(dashArray)
                                                )
                                            )
                                            val radians = Math.toRadians(startAngle.toDouble())

                                            // Calculate the coordinates for the top-left corner of the rounded rectangle
                                            val center = Offset(size.width / 2, size.height / 2)
                                            val rectTopLeftX =
                                                center.x + (innerRadius + strokeWidth / 2) * cos(
                                                    radians
                                                ).toFloat() - 12f
                                            val rectTopLeftY =
                                                center.y + (innerRadius + strokeWidth / 2) * sin(
                                                    radians
                                                ).toFloat() - 12f
                                            val pivot =
                                                Offset(rectTopLeftX + 12f, rectTopLeftY + 12f)

                                            // Rotate the canvas around the pivot point
                                            rotate(degrees = startAngle, pivot = pivot) {
                                                // Draw the rounded rectangle
                                                drawRoundRect(
                                                    color = primary,
                                                    size = Size(strokeWidth, 24f),
                                                    topLeft = Offset(rectTopLeftX, rectTopLeftY),
                                                    cornerRadius = CornerRadius(0f, 0f),
                                                    style = Stroke(
                                                        width = 12f,
                                                        pathEffect = PathEffect.cornerPathEffect(0f)
                                                    )
                                                )
                                            }

                                            // Determine the color for the segment indicator

                                            if (index == viewModel.segments.size - 1) {
                                                nextSagmentName = ""
                                            } else {
                                                nextSagmentName =
                                                    viewModel.segments[index + 1].description
                                            }

                                            var indicatorColor =
                                                if (completedSegmentIndices.contains(index - 1)) {
                                                    cyanColor
                                                } else dotColor

                                            if (viewModel.segments.lastOrNull()!!.isComplete)
                                                indicatorColor = cyanColor


                                            drawCircle(
                                                color = indicatorColor,
                                                radius = strokeWidth / 3,
                                                center = Offset(
                                                    x = (center.x + (innerRadius + strokeWidth / 2) * cos(
                                                        radians
                                                    )).toFloat(),
                                                    y = (center.y + (innerRadius + strokeWidth / 2) * sin(
                                                        radians
                                                    )).toFloat()
                                                )
                                            )

                                            // Update completed segment indices
                                            if (segment.isComplete && index < viewModel.segments.size - 1) {
                                                completedSegmentIndices.add(index)
                                            }
                                            cumulativeDuration += segmentDuration
                                            startAngle += sweepAngle
                                        }

                                    }

                                }

                                // Timer code with view
                                LaunchedEffect(isProgressStarted) {
                                    if (isProgressStarted) {
                                        segmentList.forEachIndexed { index, segmentDuration ->
                                            if (!segmentCompletion[index]) {
                                                delay((segmentDuration * totalDuration / segmentList.sum()).toLong())
                                                segmentCompletion[index] = true
                                                if (index + 1 < segmentCompletion.size) {
                                                    dotColor =
                                                        Purple45 // Change the color as desired
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.d("Timee_screen", "SegmentedProgressBar: ")
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .clickable {
                                            coroutineScope.launch {
                                                Log.d("StartButton", "StartButton: ")
                                                if (!isProgressStarted && isApiCalledSuccess == true && !isProgressCompleted) {
                                                    viewModel.setIsProgressStarted()
                                                    quickTipsShow = true
                                                    viewModel.startTimer()
                                                    viewModel.playAudioForSegment(viewModel.segments.first().audio)
                                                    isTimerRunning = true
                                                    viewModel._isFreshView.value = false
                                                    tapAnyWhereTextShow = true
                                                }
                                            }
                                        }
                                ) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    if (isProgressStarted && !isProgressCompleted) {
                                        Text(
                                            text = if (isTimerRunning) formattedTime else "00:00",
                                            fontSize = 24.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_light)),
                                            modifier = Modifier
                                                .padding(bottom = 8.dp)
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    } else if (isProgressCompleted) {
                                        Text(
                                            text = "Completed",
                                            fontSize = 24.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_light)),
                                            modifier = Modifier
                                                .padding(bottom = 8.dp)
                                                .align(Alignment.CenterHorizontally)
                                        )
                                        isTimerRunning = false
                                    } else {
                                        Text(
                                            text = "Start",
                                            fontSize = 24.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_light)),
                                            modifier = Modifier
                                                .padding(bottom = 8.dp)
                                                .align(Alignment.CenterHorizontally)
                                        )
                                    }
                                }

                                Box(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(screenHeight / 2)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.timer_underlay),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clipToBounds()
                                            .aspectRatio(1f),
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    )
                                    if (isProgressPaused) {
                                        Row(
                                            modifier = Modifier
                                                .height(100.dp)
                                                .fillMaxWidth()
                                                .align(Alignment.BottomCenter)
                                                .padding(bottom = 20.dp),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        viewModel.setIsProgressStarted()
                                                        quickTipsShow = true
                                                        viewModel.startTimer()

                                                    }
                                                },
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .height(40.dp)
                                                    .width(25.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.icon_play),
                                                    contentDescription = "",
                                                    tint = cyanColor
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            if (!isProgressPaused && isProgressStarted && isApiCalledSuccess == true && !isProgressCompleted) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(5.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = currentSegmentName ?: "",
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.dm_sans_regular))
                                    )
                                    if (tapAnyWhereTextShow) {
                                        Text(
                                            text = "Tap anywhere to pause",
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_regular))
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))


                                }
                            }

                        }


                    }
                    Box(modifier = Modifier.height(screenHeight / 2))
                    {


                        if (dataStoreViewModel!=null && dataStoreViewModel.getIsOnBoarding()!! && !quickTipsShow) {
                            QuickTip(onDoneClick = {
                                dataStoreViewModel.saveQuickTips(true)
                                dataStoreViewModel.saveIsOnBoarding(false)
                                quickTipsShow = true
                            },
                                onDNDClick = {
                                    dndMode.value = true

                                })
                        }
                        if (isProgressPaused && !isFreshView) {
                            Column(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(start = 20.dp, end = 20.dp)
                                    .padding(vertical = 50.dp)
                                    .align(alignment = Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom

                            ) {
                                androidx.compose.material3.Button(
                                    onClick = {
                                        viewModel.updateCompleteSegment(true)
                                        viewModel.setIsProgressStarted()
                                        quickTipsShow = true
                                        viewModel.startTimer()
                                        viewModel.nextSegment()
                                        if (segmentCompletion.size > 0) {
                                            segmentCompletion[currentIndex] = true
                                        }

                                    },
                                    Modifier
                                        .shadow(
                                            elevation = 44.dp,
                                            spotColor = Color(0x331B4FAD),
                                            ambientColor = Color(0x331B4FAD)
                                        )
                                        .fillMaxWidth()
                                        .height(45.dp), colors = ButtonDefaults.buttonColors(
                                        containerColor = primary, disabledContainerColor = green97
                                    )
                                ) {
                                    androidx.compose.material3.Text(
                                        text = "${nextSegmentName.toUpperCase()}",
                                        style = TextStyle(

                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                                            color = TintEyeColor,
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Column(modifier = Modifier

                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.resetTimer()//
                                        coroutineScope.launch {
                                            isApiCalled = false
                                            viewModel.resetUpdate(true)
                                            viewModel.resetAll()// Reset all states and data
                                            viewModel.resetTimer()// Reset all states and data
                                            isTimerRunning = false
                                            segmentCompletion.clear() // Clear and reinitialize the segment completion list
                                            repeat(segmentList.size) {
                                                segmentCompletion.add(false)
                                            }
                                            // Navigate or update UI as needed here
                                        }
                                    } // Trigger onStartClicked when clicked
                                ) {
                                    androidx.compose.material.Text(
                                        text = "EXIT",
                                        fontSize = 14.sp,
                                        color = cyanColor,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(Font(R.font.dm_sans_light)),
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                        if (isProgressCompleted && !isApiCalled) {
                            coroutineScope.launch {
                                if (AppUtility.isNetworkAvailable(context)) {
                                    val time = if (actualMeditationTime > 60) {
                                        actualMeditationTime
                                    } else {
                                        60
                                    }
                                    isLoading.value = true
                                    isApiCalled = true // Mark the API call as done
                                    Log.d("apimilestorn", "actualMeditationTime: 1")
                                    viewModel.mileStoneApiCall(
                                        dataStoreViewModel.getUserId()!!,
                                        time,
                                        dataStoreViewModel.getToken()!!,
                                        getCurrentDateTimeInISOFormat()
                                    )
                                    isLoading.value = false // Optionally reset isLoading
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please check your internet connection",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        if (isProgressCompleted) {


                            if (isMilestoneApiDone){
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp, vertical = 20.dp)
                                        .align(Alignment.BottomCenter),
                                ) {
                                    AddNoteExitButton(onExitClick = {
                                        viewModel.resetTimer()//
                                        isApiCalled =false
                                        coroutineScope.launch {
                                            viewModel.resetUpdate(true)
                                            viewModel.resetAll()// Reset all states and data
                                            viewModel.resetTimer()// Reset all states and data
                                            isTimerRunning = false
                                            segmentCompletion.clear() // Clear and reinitialize the segment completion list
                                            repeat(segmentList.size) {
                                                segmentCompletion.add(false)
                                            }
                                            // Navigate or update UI as needed here
                                        }
                                    },
                                        onAddNotesClick = {
                                            addNotesBottomSheetState.value = true
                                        })
                                }

                            }
                            if (mileStoneApiCallResponse.value != null
                                && mileStoneApiCallResponse.value!!.consecutiveMeditation != null
                                && mileStoneApiCallResponse.value!!.consecutiveMeditation!!.daysInARow > 1
                            ) {
                                MilestoneConsecutive(mileStoneApiCallResponse.value!!)

                            }
                            if (mileStoneApiCallResponse.value != null
                                && mileStoneApiCallResponse.value!!.newAchievements!!.isNotEmpty()
                            ) {
                                MilestoneNewAchievement(mileStoneApiCallResponse.value!!)

                            }



                        }


                    }
                }

            }


        }
    }
}

*/
