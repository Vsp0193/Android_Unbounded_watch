package com.unbounded.realizingself.component
import com.unbounded.realizingself.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unbounded.realizingself.ui.theme.green297
import com.unbounded.realizingself.ui.theme.green97


@Composable
fun Button(
    title: String,
    color: Color,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    buttonEnable: Boolean,
) {
    androidx.compose.material3.Button(
        onClick = {},
        Modifier
            .shadow(
                elevation = 44.dp,
                spotColor = Color(0x331B4FAD),
                ambientColor = Color(0x331B4FAD)
            )
            .fillMaxWidth()
            .height(44.dp),
        enabled = buttonEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = green297,
            disabledContainerColor = green97
        )
    ) {

        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.dm_sans_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                )
        )


    }

}