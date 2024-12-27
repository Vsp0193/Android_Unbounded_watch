package com.unbounded.realizingself.screens

import android.util.Log
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Chip
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import com.unbounded.realizingself.component.Toolbar
import com.unbounded.realizingself.model.segment.UserPreset
import com.unbounded.realizingself.model.segment.segmets.timer.UserTimeSegment
import com.unbounded.realizingself.ui.theme.blue
import com.unbounded.realizingself.ui.theme.cyanColor
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.secondary
import com.unbounded.realizingself.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.compareTo
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StartScreen(
data:UserTimeSegment
) {

    val context = LocalContext.current
    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTcxMCwiaWF0IjoxNzM1MjgxNDI5fQ.UFZJNMJDqLKCu24qEnfBVA97M30lDao7pjVFAlBGv88"
    val id = 1710
    val scope = rememberCoroutineScope()
   /* Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blue),
        contentAlignment = Alignment.Center
    ) {
        val isRound = context.resources.configuration.isScreenRound()
        if (!isRound) {
            Toolbar(onBackClick = {

            },
                title = "scrre1", titleColor = white
            )

        }
        val outerRadius = 400
        val centerX = size.width / 2
        val centerY = size.height / 2
        val startAngle = -90f
        val sweepAngle = 360f
        val lineLength = 8.dp.toPx()
        val lineWidth = 2.dp.toPx()
        val gapAngle = 2.0f // Adjust gap angle as needed
        val numLines = (sweepAngle / gapAngle).toInt()

        Box(modifier = Modifier.aspectRatio(1f)) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            )
            {
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
                        color = progressColor,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = lineWidth
                    )
                }
            }


        }
    }*/
}