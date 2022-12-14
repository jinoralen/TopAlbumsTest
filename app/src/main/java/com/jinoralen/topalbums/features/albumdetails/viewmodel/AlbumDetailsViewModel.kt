package com.jinoralen.topalbums.features.albumdetails.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.jinoralen.topalbums.features.albumdetails.usecase.GetAlbumDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val albumDetailsUseCase: GetAlbumDetailsUseCase
): ViewModel() {
    private val _state = MutableStateFlow<AlbumDetailsState>(AlbumDetailsState.Loading)

    val state: StateFlow<AlbumDetailsState> = _state

    private val releaseDateFormatter = DateTimeFormatterBuilder()
        .appendLiteral("Released ")
        .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
        .appendLiteral(" ")
        .appendText(ChronoField.DAY_OF_MONTH)
        .appendLiteral(", ")
        .appendText(ChronoField.YEAR)
        .toFormatter()

    fun loadAlbumDetails(albumId: Long) {
        viewModelScope.launch {
            _state.emit(AlbumDetailsState.Loading)

            when(val result = albumDetailsUseCase(albumId)) {
                is Either.Left -> {
                    _state.emit(AlbumDetailsState.Error(result.value.message))
                }
                is Either.Right -> {
                    val album = result.value

                    _state.emit(AlbumDetailsState.UiState(
                        artistName = album.artistName,
                        albumName = album.name,
                        releaseDateString = album.releaseDate.format(releaseDateFormatter),
                        artwork = album.artwork,
                        albumUrl = Uri.parse(album.url),
                        copyright = album.albumCopyright,
                        genre = album.genre
                    ))
                }
            }
        }
    }
}
