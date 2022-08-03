package com.jinoralen.topalbums.domain.model

data class AlbumInfo(
    val id: Long,
    val artistName: String,
    val name: String,
    val artwork: String,
)

fun Album.toAlbumInfo() = AlbumInfo(id, artistName, name, artwork)
