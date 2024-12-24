package com.peak.unbounded.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.unbounded.realizingself.ui.theme.titleColor
import com.unbounded.realizingself.R
import com.unbounded.realizingself.ui.theme.blue
import com.unbounded.realizingself.ui.theme.cyanColor
import com.unbounded.realizingself.ui.theme.grayE7
import com.unbounded.realizingself.ui.theme.sefron

@Composable
fun CustomDialog(
    title: String,
    discription: String,
    yesClick: () -> Unit,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "$title",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(700),
                            color = titleColor,
                        ),
                        modifier = Modifier
                            .padding(start = 6.dp, bottom = 5.dp)
                            .fillMaxWidth()
                    )
                    // Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "$discription",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = titleColor,
                        ),
                        modifier = Modifier
                            .padding(start = 6.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    )



                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(
                        Modifier
                            .fillMaxWidth()
                            .height(0.1.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clipToBounds(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                            .weight(.5f)
                            .clickable(
                                onClick = {
                                    setShowDialog(false)
                                }
                            )) {

                            Text(
                                text = "No",
                                color = cyanColor,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 49.sp,
                                    fontFamily = FontFamily(Font(R.font.dm_sans_bold)),
                                    fontWeight = FontWeight(700),
                                    color = titleColor,
                                ),
                                modifier = Modifier
                                    .padding(start = 6.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                            )
                        }
                        VerticalDivider(
                            Modifier
                                .width(0.3.dp)
                                .height(56.dp)
                                .clipToBounds()
                                .background(grayE7)
                        )
                        Box(modifier = Modifier
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                            .weight(.5f)
                            .clickable(
                                onClick = {
                                    yesClick()
                                    setShowDialog(false)
                                }
                            )) {

                            Text(
                                text = "Yes",
                                color = cyanColor,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 49.sp,
                                    fontFamily = FontFamily(Font(R.font.dm_sans_bold)),
                                    fontWeight = FontWeight(700),
                                    color = titleColor,
                                ),
                                modifier = Modifier
                                    .padding(start = 6.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun AdminLeaveGroupDialog(
    title: String,
    description: String,
    onReassignClick: () -> Unit,
    onProceedClick: () -> Unit,
    onCancelClick: () -> Unit,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = title,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(700),
                            color = titleColor,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = description,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 12.sp,
                           // lineHeight = 49.sp,
                            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                            fontWeight = FontWeight(400),
                            color = titleColor,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(Modifier.fillMaxWidth())
                    DialogButton(text = "Reassign", onClick = { onReassignClick() })
                    HorizontalDivider(Modifier.fillMaxWidth())
                    DialogButton(text = "Proceed", onClick = { onProceedClick() })
                    HorizontalDivider(Modifier.fillMaxWidth())
                    DialogButton(text = "Cancel", onClick = { onCancelClick() })
                }
            }
        }
    }
}

@Composable
private fun DialogButton(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = if (text == "Proceed") sefron else blue,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 49.sp,
            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
            fontWeight = FontWeight(700),
            color = titleColor,
        ),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    )
}
