package com.unbounded.realizingself.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun TimerScreen(){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Screen One", color = Color.White)
    }
}