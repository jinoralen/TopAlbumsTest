package com.jinoralen.topalbums

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.jinoralen.topalbums.ui.TopAlbumsApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Disable default handling of window borders (e.g. stretch UI to cover entire chart)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TopAlbumsApp()
        }
    }
}
