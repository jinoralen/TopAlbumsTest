package com.jinoralen.topalbums.features.albumlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jinoralen.topalbums.domain.model.AlbumInfo
import com.jinoralen.topalbums.ui.theme.Grey
import com.jinoralen.topalbums.ui.theme.TopAlbumsTheme

@Preview
@Composable
fun AlbumCardPreview() {
    TopAlbumsTheme {
        AlbumCard(album = AlbumInfo(
            id = 1636789969,
            artistName = "Beyoncé",
            name = "RENAISSANCE",
            artwork = "https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/fe/ba/43/feba43be-99e8-ad8c-9fad-1bfdea7a4e98/196589344267.jpg/100x100bb.jpg",
        ), modifier = Modifier.size(512.dp))
    }
}

@Composable
fun AlbumCard(album: AlbumInfo, modifier: Modifier = Modifier, onClick: (Long) -> Unit = {}) {
    Card(
        modifier = modifier.fillMaxSize().clickable {
            onClick(album.id)
        },
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(album.artwork)
                    .build(),
                contentDescription = album.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.BottomStart)) {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                )

                Text(
                    text = album.artistName,
                    style = MaterialTheme.typography.caption,
                    color = Grey
                )
            }
        }
    }
}
