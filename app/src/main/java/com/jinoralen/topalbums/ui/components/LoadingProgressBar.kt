package com.jinoralen.topalbums.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingProgressBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){
        Text("Loading", modifier = Modifier.align(Alignment.Center))
    }
}
