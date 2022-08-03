package com.jinoralen.topalbums.domain.repository.cache.room

import androidx.paging.PagingSource
import androidx.room.*
import com.jinoralen.topalbums.domain.model.Album

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    @Query("DELETE FROM topAlbums ")
    suspend fun deleteAllAlbums()

    @Transaction
    suspend fun updateAlbums(albums: List<Album>) {
        deleteAllAlbums()
        insertAlbums(albums)
    }

    @Query("SELECT * FROM topAlbums")
    fun getAlbums(): PagingSource<Int, Album>

    @Query("SELECT * FROM topAlbums WHERE :albumId == id")
    suspend fun getAlbumById(albumId: Long): Album?
}
