package com.unbounded.realizingself.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import com.unbounded.realizingself.NAVIGATION.OnBoardingGraph
import com.unbounded.realizingself.R
import com.unbounded.realizingself.ui.theme.black
import com.unbounded.realizingself.ui.theme.checkFF25A3DA
import com.unbounded.realizingself.ui.theme.white

@Composable
fun StartScreen(
    navHostController: NavHostController,
) {

    val context = LocalContext.current

    val configuration = LocalConfiguration.current


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(black),
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
                            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween, // This will space the items between left and right

                    ) {
                        // IconButton and Image on the left
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start, // This will space the items between left and right
                            verticalAlignment = Alignment.CenterVertically // Vertically align the items (optional, for better alignment)
                        ) {
                            IconButton(
                                onClick = {

                                },
                                modifier = Modifier.align(Alignment.CenterVertically).wrapContentSize().weight(.2f) // Align icon vertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.back_buton),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.width(12.dp).height(12.dp)
                                )
                            }

                            // Title Text on the left
                            Text(
                                modifier = Modifier.weight(1f), // Align text vertically
                                text = "Unbounded",
                                color = Color.White,
                                textAlign = TextAlign.Start,
                                fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),
                            )
                        }
                        // TimeText on the right
                        TimeText(
                            modifier = Modifier.align(Alignment.CenterVertically).weight(.3f).padding(top=3.dp), // Align vertically to match the row items
                            timeTextStyle = TimeTextDefaults.timeTextStyle(
                                color = Color.White,
                                fontSize = 14.sp,
                            ),

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
                Canvas(
                    modifier = Modifier.fillMaxSize()
                )
                {


                    val screenHeight = configuration.screenHeightDp.dp
                    val screenWidth = configuration.screenWidthDp.dp
                    val outerRadius = (screenWidth/3).toPx()
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val startAngle = -90f
                    val sweepAngle = 360f
                    val lineLength = 8.dp.toPx()
                    val lineWidth = 2.dp.toPx()
                    val gapAngle = 2.0f // Adjust gap angle as needed
                    val numLines = (sweepAngle / gapAngle).toInt()
                    val currentProgress = 1f // Access animation progress
                    val progressStartAngle = startAngle
                    val progressSweepAngle =
                        currentProgress * sweepAngle // Adjust progress to sweep angle
                    val numProgressLines =
                        (progressSweepAngle / gapAngle).toInt()// Adjust progress to sweep angle

                    for (i in 0 until numProgressLines!!) {
                        val angle = progressStartAngle + i * gapAngle
                        val startX =
                            centerX + (outerRadius - lineLength / 2) * kotlin.math.cos(
                                Math.toRadians(angle.toDouble())
                            ).toFloat()
                        val startY =
                            centerY + (outerRadius - lineLength / 2) * kotlin.math.sin(
                                Math.toRadians(angle.toDouble())
                            ).toFloat()
                        val endX =
                            centerX + (outerRadius + lineLength / 2) * kotlin.math.cos(
                                Math.toRadians(angle.toDouble())
                            ).toFloat()
                        val endY =
                            centerY + (outerRadius + lineLength / 2) * kotlin.math.sin(
                                Math.toRadians(angle.toDouble())
                            ).toFloat()

                        drawLine(
                            color = checkFF25A3DA,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = lineWidth
                        )
                    }
                }

                // Material3 Text in the center
               Text(
                    text = "Start",
                    color = Color.White,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Center).clickable{
                        navHostController.navigate(route = OnBoardingGraph.PagerScreen.route)
                    }
                )
            }

        }

   /* Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blue),
        contentAlignment = Alignment.Center
    ) {
        val isRound = context.resources.configuration.isScreenRound()
        if (!isRound) {
            Toolbar(
                onBackClick = {

                },
                title = "scrre1", titleColor = white
            )

        }


    }*/
}