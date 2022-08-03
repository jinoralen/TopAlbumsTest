package com.jinoralen.topalbums.domain.model

import androidx.room.PrimaryKey
import com.jinoralen.topalbums.core.json.DateString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class TopAlbumsResponse(
    val feed: FeedResponse
)

@JsonClass(generateAdapter = true)
data class FeedResponse(
    val copyright: String,
    val results: List<AlbumResponse>
)

@JsonClass(generateAdapter = true)
data class AlbumResponse(
    @PrimaryKey
    val id: Long,

    val artistName: String,
    val name: String,
    @DateString
    val releaseDate: LocalDate,
    @Json(name = "artworkUrl100")
    val artwork: String,
    val genres: List<GenreResponse>,
    val url: String,
)

@JsonClass(generateAdapter = true)
data class GenreResponse(
    val name: String
)

fun AlbumResponse.toDomain(copyright: String) = Album(
    id = id,
    artistName = artistName,
    name = name,
    releaseDate = releaseDate,
    artwork = artwork,
    genre = genres.firstOrNull()?.name,
    url = url,
    albumCopyright = copyright
)
