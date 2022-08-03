package com.jinoralen.topalbums.domain.repository.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.jinoralen.topalbums.core.di.IoDispatcher
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.model.AlbumDetails
import com.jinoralen.topalbums.domain.model.AlbumInfo
import com.jinoralen.topalbums.domain.model.toAlbumInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AlbumsRepository {
    suspend fun getTopAlbums(count: Int): Either<CallError, List<Album>>

    suspend fun getTopAlbumsInfo(): Either<CallError, List<AlbumInfo>>

    suspend fun getAlbumDetails(albumId: Long): Either<CallError, AlbumDetails>
}

class RemoteAlbumsRepositoryImpl @Inject constructor(
    private val service: AlbumDataService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AlbumsRepository {
    override suspend fun getTopAlbums(count: Int): Either<CallError, List<Album>> = withContext(ioDispatcher) {
        service.getTopAlbums(count).map { response ->
            response.feed.results
        }
    }

    override suspend fun getTopAlbumsInfo(): Either<CallError, List<AlbumInfo>> = withContext(ioDispatcher) {
        service.getTopAlbums().map { response ->
            response.feed.results.map{album -> album.toAlbumInfo() }
        }
    }

    override suspend fun getAlbumDetails(albumId: Long): Either<CallError, AlbumDetails> = withContext(ioDispatcher) {
        service.getTopAlbums().map { response ->
            val copyright = response.feed.copyright
            val album = response.feed.results.first { album -> album.id == albumId }

            AlbumDetails(album, copyright)
        }
    }
}

