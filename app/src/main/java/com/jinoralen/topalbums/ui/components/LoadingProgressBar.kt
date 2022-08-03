package com.jinoralen.topalbums.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jinoralen.topalbums.ui.theme.TopAlbumsTheme

@Preview
@Composable
fun LoadingProgressBarPreview() {
    TopAlbumsTheme {
        LoadingProgressBar(Modifier.fillMaxSize())
    }
}

@Composable
fun LoadingProgressBar(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){
        Column(
            modifier = Modifier.wrapContentSize().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text("Loading")
        }
    }
}
