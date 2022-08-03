package com.jinoralen.topalbums.features.albumlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jinoralen.topalbums.features.albumlist.usecase.GetTopAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    getTopAlbumsUseCase: GetTopAlbumsUseCase,
): ViewModel() {

    val state = getTopAlbumsUseCase()
        .cachedIn(viewModelScope)
}
