package com.jinoralen.topalbums.domain.repository.cache

import androidx.paging.PagingSource
import com.jinoralen.topalbums.core.di.IoDispatcher
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.model.AlbumDetails
import com.jinoralen.topalbums.domain.repository.cache.room.AlbumDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AlbumsCacheRepository {
    fun getTopAlbums(): PagingSource<Int, Album>

    suspend fun getAlbumDetails(albumId: Long): AlbumDetails?

    suspend fun update(albums: List<Album>)
}

class RoomAlbumsCacheRepositoryImpl @Inject constructor(
    private val albumDao: AlbumDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): AlbumsCacheRepository {
    override fun getTopAlbums(): PagingSource<Int, Album> = albumDao.getAlbums()

    override suspend fun getAlbumDetails(albumId: Long): AlbumDetails? {
        return albumDao.getAlbumById(albumId)?.let { AlbumDetails(it, "Copy") }
    }

    override suspend fun update(albums: List<Album>) = withContext(ioDispatcher) {
         albumDao.updateAlbums(albums)
    }
}
