package com.jinoralen.topalbums.features.albumdetails.usecase

import arrow.core.Either
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.repository.cache.AlbumsCacheRepository
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(
    private val cacheRepository: AlbumsCacheRepository,
) {
    suspend operator fun invoke(albumId: Long): Either<AlbumDetailsError, Album>  {
        // There is no need to do network request
        // since we navigate from screen that has access to all information
        return cacheRepository.getAlbumDetails(albumId)?.let { Either.Right(it) }
            ?: Either.Left(AlbumDetailsError.NoData)
    }

    sealed class AlbumDetailsError(val message: String) {
        object NoData: AlbumDetailsError("No data in cache")
    }
}
