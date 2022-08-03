package com.jinoralen.topalbums.features.albumlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.jinoralen.topalbums.domain.repository.network.AlbumsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val remoteDataRepository: AlbumsRepository
): ViewModel() {

    private val _state = MutableStateFlow<AlbumListState>(AlbumListState.Loading)
    val state: StateFlow<AlbumListState> = _state

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            when(val result = remoteDataRepository.getTopAlbums()) {
                is Either.Left -> { }
                is Either.Right -> {
                    _state.emit(AlbumListState.UiState(result.value))
                }
            }
        }
    }
}
