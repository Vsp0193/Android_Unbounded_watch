package com.unbounded.realizingself.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.unbounded.realizingself.R
import com.peak.unbounded.features.common.NoRippleInteractionSource


@Composable
fun TextRegular(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
        )
    )
}

@Composable
fun TextRegular2(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    btnStatus:Boolean,
    onClick:()->Unit,
) {
    Text(
        text = title,
        modifier = Modifier.clickable {
            onClick()

        },
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
        )
    )
}

@Composable
fun TextRegular3(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    btnStatus:Boolean,
    onClick:()->Unit,
) {
    Text(
        text = title,
        modifier = Modifier
            .padding(end=5.dp)
            .width(60.dp)
            .clickable (
                onClick = {
                    onClick()
                },
                indication = null,
                interactionSource = NoRippleInteractionSource().also {  it },

                ),
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
            textAlign = TextAlign.End
        )
    )
}
@Composable
fun TextMedium(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_medium)),
        )
    )

}

@Composable
fun TextSemiBold(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_semibold)),
        )
    )


}

@Composable
fun TextBold(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
) {
    Text(
        text = title,
        style = TextStyle(
            fontSize = fontSize,
            lineHeight = lineHeight,
            fontWeight = fontWeight,
            color = color,
            fontFamily = FontFamily(Font(R.font.dm_sans_bold)),
        )
    )


}