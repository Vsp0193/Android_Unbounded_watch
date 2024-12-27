package com.unbounded.realizingself.core.base
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.core.view.WindowCompat
import com.unbounded.realizingself.ui.theme.RealizingSelfTheme
import com.unbounded.realizingself.ui.theme.primary
import com.unbounded.realizingself.ui.theme.secondary


abstract class BaseComponentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RealizingSelfTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background( brush = Brush
                            .verticalGradient(colors = listOf(primary, secondary))
                ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }

    @Composable
    abstract fun Content()
}