package com.jinoralen.topalbums.features.albumdetails.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinoralen.topalbums.ui.theme.Blue
import com.jinoralen.topalbums.ui.theme.TopAlbumsTheme

@Preview
@Composable
fun OutlinedTextPreview() {
    TopAlbumsTheme {
        OutlinedText(text = "Pop")
    }
}

@Composable
fun OutlinedText(text: String,  modifier: Modifier = Modifier, color: Color = Blue) {
    Box(modifier = modifier
        .border(width = 1.dp, shape = RoundedCornerShape(50), color = color)
        .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.caption
        )
    }
}
