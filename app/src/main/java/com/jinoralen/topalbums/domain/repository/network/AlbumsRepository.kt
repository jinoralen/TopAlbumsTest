package com.jinoralen.topalbums.domain.repository.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.jinoralen.topalbums.core.di.IoDispatcher
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.model.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

interface AlbumsRepository {
    suspend fun getTopAlbums(count: Int): Either<CallError, List<Album>>
}

class RemoteAlbumsRepositoryImpl @Inject constructor(
    private val service: AlbumDataService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AlbumsRepository {
    override suspend fun getTopAlbums(count: Int): Either<CallError, List<Album>> = withContext(ioDispatcher) {
        service.getTopAlbums(count).map { response ->
            Timber.d(response.toString())

            val copyright = response.feed.copyright
            response.feed.results.map { albumResponse ->
                albumResponse.toDomain(copyright)
            }
        }
    }
}

