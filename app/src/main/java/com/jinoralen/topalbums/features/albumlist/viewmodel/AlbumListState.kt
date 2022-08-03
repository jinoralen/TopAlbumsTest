package com.jinoralen.topalbums.features.albumlist.viewmodel

import com.jinoralen.topalbums.domain.model.AlbumInfo

sealed class AlbumListState {
    data class UiState(val albums: List<AlbumInfo>): AlbumListState()
    object Loading: AlbumListState()
}


