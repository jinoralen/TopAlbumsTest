package com.jinoralen.topalbums.features.albumlist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jinoralen.topalbums.R
import com.jinoralen.topalbums.domain.model.AlbumInfo
import com.jinoralen.topalbums.features.albumlist.viewmodel.AlbumListState
import com.jinoralen.topalbums.features.albumlist.viewmodel.AlbumListViewModel
import com.jinoralen.topalbums.ui.components.LoadingProgressBar
import com.jinoralen.topalbums.ui.theme.TopAlbumsTheme
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun AlbumListScreen(openDetails: (Long) -> Unit, modifier: Modifier = Modifier, viewModel: AlbumListViewModel = hiltViewModel()) {
    val collapsingToolbarState = rememberCollapsingToolbarScaffoldState()
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(true) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            // currently we have white background, so use dark icons
            // but this can be customized for dark theme
            darkIcons = true
        )
    }

    CollapsingToolbarScaffold(
        state = collapsingToolbarState,
        toolbar = {

            val minTextSize = 16
            val maxTextSize = 34
            val textSize = (minTextSize + (maxTextSize - minTextSize) * collapsingToolbarState.toolbarState.progress).sp

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .pin()
            )

            Text(
                text = stringResource(id = R.string.top_albums),
                style = MaterialTheme.typography.h1,
                color = Color.Black,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .road(Alignment.Center, Alignment.BottomStart)
                ,
                fontSize = textSize,
            )
        },
        modifier = modifier,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed
    ) {
        val state by viewModel.state.collectAsState()

        AlbumListScreen(state, openDetails)
    }
}

@Composable
private fun AlbumListScreen(
    state: AlbumListState,
    openDetails: (Long) -> Unit
) {
    when (state) {
        AlbumListState.Loading -> {
            LoadingProgressBar()
        }
        is AlbumListState.UiState -> {
            AlbumList(state.albums, openDetails)
        }
    }
}


@Preview
@Composable
fun AlbumListPreview() {
    TopAlbumsTheme {
        AlbumList(albums = listOf(
            AlbumInfo(
                id = 1636789969,
                artistName = "Beyoncé",
                name = "RENAISSANCE",
                artwork = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/fe/ba/43/feba43be-99e8-ad8c-9fad-1bfdea7a4e98/196589344267.jpg/100x100bb.jpg",
            ),
            AlbumInfo(
                id = 1622045624,
                artistName = "Bad Bunny",
                name = "Un Verano Sin Ti",
                artwork = "https://is4-ssl.mzstatic.com/image/thumb/Music122/v4/6d/31/ab/6d31abaf-7a07-05f1-13ad-72ec520b6bfb/22UMGIM67374.rgb.jpg/100x100bb.jpg",
            ),
        ))
    }
}

@Composable
private fun AlbumList(albums: List<AlbumInfo>, openDetails: (Long) -> Unit = {}) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(albums.size) { index ->
            AlbumCard(albums[index], Modifier.padding(8.dp), openDetails)
        }
    }
}
