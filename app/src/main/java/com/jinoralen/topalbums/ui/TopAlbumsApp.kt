package com.jinoralen.topalbums.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jinoralen.topalbums.features.albumdetails.ui.AlbumDetailsScreen
import com.jinoralen.topalbums.features.albumlist.ui.AlbumListScreen
import com.jinoralen.topalbums.ui.theme.TopAlbumsTheme

@Composable
fun TopAlbumsApp() {
    TopAlbumsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val navHostController = rememberNavController()

            NavHost(navController = navHostController, startDestination = "AlbumList") {
                composable("AlbumList") {
                    AlbumListScreen(
                        openDetails = { albumId -> navHostController.navigate("AlbumDetails/${albumId}")},
                        modifier = Modifier.systemBarsPadding()
                    )
                }
                composable(
                    route = "AlbumDetails/{albumId}",
                    arguments = listOf(
                        navArgument("albumId") { type = NavType.LongType }
                    )
                ) { backStackEntry ->
                    backStackEntry.arguments?.getLong("albumId")?.let{ albumId ->
                        AlbumDetailsScreen(
                            albumId = albumId,
                            navController = navHostController,
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }
                }
            }
        }
    }
}
