package com.jinoralen.topalbums.features.albumlist.usecase

import android.content.Context
import androidx.paging.*
import androidx.paging.RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH
import androidx.paging.RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH
import arrow.retrofit.adapter.either.networkhandling.HttpError
import arrow.retrofit.adapter.either.networkhandling.IOError
import arrow.retrofit.adapter.either.networkhandling.UnexpectedCallError
import com.jinoralen.topalbums.core.di.IoDispatcher
import com.jinoralen.topalbums.core.isConnected
import com.jinoralen.topalbums.domain.model.AlbumInfo
import com.jinoralen.topalbums.domain.repository.cache.AlbumsCacheRepository
import com.jinoralen.topalbums.domain.repository.network.AlbumsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopAlbumsUseCase @Inject constructor(
    private val albumsRepository: AlbumsRepository,
    private val albumsCacheRepository: AlbumsCacheRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(): Flow<PagingData<AlbumInfo>> = Pager(
        config = PagingConfig(100),
        remoteMediator = TopAlbumsRemoteMediator(
            albumsRepository,
            albumsCacheRepository,
            context
        ),
    ){
        albumsCacheRepository.getTopAlbums()
    }.flow.flowOn(ioDispatcher)


    @OptIn(ExperimentalPagingApi::class)
    private class TopAlbumsRemoteMediator(
        private val albumsRepository: AlbumsRepository,
        private val albumsCacheRepository: AlbumsCacheRepository,
        private val context: Context
    ): RemoteMediator<Int, AlbumInfo>() {

        override suspend fun initialize(): InitializeAction {
            return if(context.isConnected()) LAUNCH_INITIAL_REFRESH else SKIP_INITIAL_REFRESH
        }

        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, AlbumInfo>
        ): MediatorResult {
            return when(loadType){
                LoadType.REFRESH -> refreshAlbums(state.config.pageSize)
                LoadType.PREPEND, LoadType.APPEND -> {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            }
        }

        private suspend fun refreshAlbums(size: Int): MediatorResult {
            return albumsRepository.getTopAlbums(size).fold(
                ifLeft = { error ->
                    when(error) {
                        is HttpError -> MediatorResult.Error(IllegalArgumentException(error.message))
                        is IOError -> MediatorResult.Error(error.cause)
                        is UnexpectedCallError -> MediatorResult.Error(error.cause)
                    }
                },
                ifRight = { albums ->
                    albumsCacheRepository.update(albums)

                    MediatorResult.Success(endOfPaginationReached = albums.isEmpty())
                }
            )
        }
    }
}
