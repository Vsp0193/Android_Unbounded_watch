package com.vsple.review.features.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unbounded.realizingself.ui.theme.ButtonDisable
import com.unbounded.realizingself.ui.theme.DividerColor
import com.unbounded.realizingself.ui.theme.RealizingSelfTheme
import com.unbounded.realizingself.ui.theme.secondary


@Composable
fun ButtonWithTopDivider(
    modifier: Modifier = Modifier,
    title: String,
    textColor: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    dividerVisibility: Float = 1f,
    buttonDividerSpacer: Dp,
    buttonEnable: Boolean = false,
    buttonHorizontalPadding: Dp,
    buttonVerticalPadding: Dp,
    onClick: () -> Unit
    ) {
    Column {
        Divider(
            color = DividerColor,
            modifier = modifier
                .fillMaxWidth()
                .alpha(dividerVisibility)
                .height(1.dp)
        )
        Spacer(modifier = modifier.height(buttonDividerSpacer))
        Button(
            onClick = {
                onClick.invoke()
            },
            modifier
                .fillMaxWidth()
                .padding(horizontal = buttonHorizontalPadding, vertical = buttonVerticalPadding)
                .height(50.dp),
            enabled = buttonEnable,
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = secondary,
                disabledContainerColor = ButtonDisable
            )
        ) {
            Text(
                text = title,
                lineHeight = lineHeight,
                style = TextStyle(
                    fontSize = fontSize,
//                fontFamily = FontFamily(Font(R.font.dm_sans_bold)),
                    fontWeight = fontWeight,
                    color = textColor,
                )
            )


        }
    }


}

@Preview
@Composable
fun ButtonPreview() {
    RealizingSelfTheme() {
        ButtonWithTopDivider(
            title = "Gurmeet",
            textColor = Color.White,
            fontWeight = FontWeight(500),
            fontSize = 24.sp,
            buttonDividerSpacer = 10.dp,
            dividerVisibility = 1f,
            lineHeight = TextUnit.Unspecified,
            buttonEnable = true,
            buttonHorizontalPadding = 40.dp,
            buttonVerticalPadding = 10.dp
        ){

        }
    }
}