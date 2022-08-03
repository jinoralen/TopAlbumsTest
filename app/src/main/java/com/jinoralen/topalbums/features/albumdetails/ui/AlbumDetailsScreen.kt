package com.jinoralen.topalbums.features.albumdetails.ui

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jinoralen.topalbums.R
import com.jinoralen.topalbums.features.albumdetails.viewmodel.AlbumDetailsState
import com.jinoralen.topalbums.features.albumdetails.viewmodel.AlbumDetailsViewModel
import com.jinoralen.topalbums.ui.components.LoadingProgressBar
import com.jinoralen.topalbums.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AlbumDetailsScreen(
    albumId: Long,
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailsViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = albumId) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            // according to specs we have white icons
            // but this can be customized if API provided some color of artwork
            darkIcons = false
        )

        coroutineScope.launch {
            viewModel.loadAlbumDetails(albumId)
        }
    }

    val state by viewModel.state.collectAsState()

    AlbumDetailsScreen(state, modifier)
}

@Composable
private fun AlbumDetailsScreen(
    state: AlbumDetailsState,
    modifier: Modifier
) {
    when (state) {
        AlbumDetailsState.Loading -> {
            LoadingProgressBar()
        }
        is AlbumDetailsState.UiState -> {
            val context = LocalContext.current
            AlbumDetails(
                state = state,
                modifier = modifier,
                onClickVisitAlbum = {
                    CustomTabsIntent.Builder().build().launchUrl(context, state.albumUrl)
                }

            )
        }
        is AlbumDetailsState.Error -> {
            val context = LocalContext.current
            
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
    }
}

@Preview
@Composable
fun AlbumDetailsPreview(){
    TopAlbumsTheme {
        AlbumDetails(
            state = AlbumDetailsState.UiState(
                artistName = "Beyoncé",
                albumName = "RENAISSANCE",
                releaseDateString = "2022-07-29",
                artwork = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/fe/ba/43/feba43be-99e8-ad8c-9fad-1bfdea7a4e98/196589344267.jpg/100x100bb.jpg",
                genre = "Pop",
                albumUrl = Uri.parse("https://music.apple.com/us/album/renaissance/1636789969"),
                copyright = "Copyright © 2022 Apple Inc. All rights reserved."
            )
        )
    }
}

@Composable
private fun AlbumDetails(
    state: AlbumDetailsState.UiState,
    modifier: Modifier = Modifier,
    onClickVisitAlbum: () -> Unit = {},
) {
    Column(modifier = modifier
        .fillMaxSize(),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.artwork)
                .build(),
            contentDescription = state.albumName,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Column(Modifier.padding(16.dp).weight(1f).verticalScroll(rememberScrollState())) {
            Text(
                text = state.artistName,
                style = MaterialTheme.typography.body1,
                color = DetailsGrey
            )

            Text(
                text = state.albumName,
                style = MaterialTheme.typography.h1,
                color = Dark,
            )

            state.genre?.let {
                OutlinedText(text = it)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = state.releaseDateString,
                style = MaterialTheme.typography.caption,
                color = Grey,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.copyright,
                style = MaterialTheme.typography.caption,
                color = Grey,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }

        Button(
            onClick = onClickVisitAlbum,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue,
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        {
            Text(
                text = stringResource(id = R.string.visit_album),
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
