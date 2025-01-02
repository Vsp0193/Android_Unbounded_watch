package com.unbounded.realizingself.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import com.unbounded.realizingself.DataTransferViewModel
import com.unbounded.realizingself.NAVIGATION.OnBoardingGraph
import com.unbounded.realizingself.R
import com.unbounded.realizingself.ui.theme.black
import com.unbounded.realizingself.ui.theme.checkFF25A3DA
import com.unbounded.realizingself.ui.theme.white

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PagerScreen( dataTransferViewModel: DataTransferViewModel,  navHostController: NavHostController,){
    val context = LocalContext.current

    val configuration = LocalConfiguration.current

    val maxPages = 2
 val pagerState = rememberPagerState(initialPage = 0) { maxPages }
    //     val pagerState = rememberPagerState()

    val pageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = pagerState.currentPageOffsetFraction
            override val selectedPage: Int
                get() = pagerState.currentPage
            override val pageCount: Int
                get() = maxPages
        }
    }
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
                .weight(.6f),
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
                    TimeText(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            } else {
                // For non-round screens: hello_world in the top-left, TimeText in the top-right
               /* Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 0.dp, top = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, // This will space the items between left and right
                ) {


                }*/
                TimeText(
                    modifier = Modifier.padding(top=3.dp), // Align vertically to match the row items
                    timeTextStyle = TimeTextDefaults.timeTextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                    ),


                    )
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


            // Material3 Text in the center
            HorizontalPager(
                state = pagerState,
             //   pageCount = 2, // Number of screens
                modifier = Modifier,
                pageContent = { page ->
                    when (page) {

                        0 -> TimerScreen();
                        1 -> TimerButtonScreen(dataTransferViewModel)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add HorizontalPageIndicator
            HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState,
                selectedColor = Color.Blue, // Replace with your colorAccent
                unselectedColor = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp)
            )
        }


    }
}