package com.jinoralen.topalbums.features.albumdetails.viewmodel

import android.net.Uri

sealed class AlbumDetailsState {
    data class UiState(
        val artistName: String,
        val albumName: String,
        val releaseDateString: String,
        val artwork: String,
        val genre: String?,
        val albumUrl: Uri,
        val copyright: String
    ): AlbumDetailsState()
    object Loading: AlbumDetailsState()
}
