package com.jinoralen.topalbums.domain.repository.cache

import androidx.paging.PagingSource
import com.jinoralen.topalbums.core.di.IoDispatcher
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.model.AlbumInfo
import com.jinoralen.topalbums.domain.repository.cache.room.AlbumDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AlbumsCacheRepository {
    fun getTopAlbums(): PagingSource<Int, AlbumInfo>

    suspend fun getAlbumDetails(albumId: Long): Album?

    suspend fun update(albums: List<Album>)
}

class RoomAlbumsCacheRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AlbumsCacheRepository {
    override fun getTopAlbums(): PagingSource<Int, AlbumInfo> = albumDao.getAlbumsInfo()

    override suspend fun getAlbumDetails(albumId: Long): Album? {
        return albumDao.getAlbumById(albumId)
    }

    override suspend fun update(albums: List<Album>) = withContext(ioDispatcher) {
         albumDao.updateAlbums(albums)
    }
}
