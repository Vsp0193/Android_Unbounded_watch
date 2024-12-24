package com.unbounded.realizingself.ui.theme.componentutils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.peak.unbounded.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    isVisible: Boolean,
    appBarColor: Color,
    titleColor: Color,
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    TopAppBar(

        title = {
            Row(
                //  verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
            ) {
                TextRegular(
                    title = title,
                    color = titleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 21.sp
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = appBarColor
        ),
        navigationIcon = {
            if (isVisible) {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        // imageVector = Icons.Filled.KeyboardArrowLeft,
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = androidx.core.R.drawable.ic_call_decline),
                    contentDescription = null,
                    tint = Color.Transparent
                )
            }
        },
        actions = actions,
    )


}

@Preview(showSystemUi = true)
@Composable
fun ToolbarPreview(
) {
    Toolbar("ssas", isVisible = true,
        appBarColor = Color.Transparent,
        titleColor = Color.Black,
        onBackClick = {}) {

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithAction(
    title: String,
    actionTitle: String,
    isVisible: Boolean,
    appBarColor: Color,
    titleColor: Color,
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    onClick: () -> Unit,
) {
    TopAppBar(

        title = {
            Row(
                //  verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                  .padding(start = 10.dp)
            ) {
                TextRegular(
                    title = title,
                    color = titleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 21.sp
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = appBarColor
        ),
        navigationIcon = {
            if (isVisible) {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        // imageVector = Icons.Filled.KeyboardArrowLeft,
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = androidx.core.R.drawable.ic_call_decline),
                    contentDescription = null,
                    tint = Color.Transparent
                )
            }
        },
        actions = {
            TextRegular3(
                    title = actionTitle,
                    color = titleColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 21.sp,
                btnStatus = true,
               onClick = {
                   onClick()
               }
                )
        }

    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarMeditationRoutine(
    title: String,
    appBarColor: Color,
    titleColor: Color,
    onBackClick: () -> Unit,
    onClick: () -> Unit,
) {
    TopAppBar(

        title = {
            Row(
                //  verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                  .padding(start = 10.dp)
            ) {
                TextRegular(
                    title = title,
                    color = titleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 21.sp
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = appBarColor
        ),
        navigationIcon = {

                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        // imageVector = Icons.Filled.KeyboardArrowLeft,
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

        },
        actions = {

            IconButton(
                onClick =      {
                    onClick()
                }
            ) {
                Icon(
                    // imageVector = Icons.Filled.KeyboardArrowLeft,
                    imageVector = ImageVector.vectorResource(id = R.drawable.nav_timer_active_colored),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }


        }

    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarEditGroup(
    painer:Painter,
    title: String,
    appBarColor: Color,
    titleColor: Color,
    actionImgHeight: Dp,
    actionImgWidth: Dp,
    onBackClick: () -> Unit,
    onClick: () -> Unit,
) {
    TopAppBar(

        title = {
            Row(
                //  verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                  .padding(start = 10.dp)
            ) {
                TextRegular(
                    title = title,
                    color = titleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 21.sp
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = appBarColor
        ),
        navigationIcon = {

                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        // imageVector = Icons.Filled.KeyboardArrowLeft,
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

        },
        actions = {

            IconButton(
                onClick =      {
                    onClick()
                }
            ) {
                Image(

                    painter = painer,
                    contentDescription = null,
                    modifier = Modifier
                        .height(actionImgHeight).width(actionImgWidth),
                        //.aspectRatio(1f),
                    contentScale = ContentScale.FillBounds
                )
            }


        }

    )
}




