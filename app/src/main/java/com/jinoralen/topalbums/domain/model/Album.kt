package com.jinoralen.topalbums.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jinoralen.topalbums.core.json.DateString
import com.squareup.moshi.Json
import java.time.LocalDate

@Entity(
    tableName = "topAlbums"
)
data class Album(
    @PrimaryKey
    val id: Long,

    val artistName: String,
    val name: String,
    @DateString
    val releaseDate: LocalDate,
    @Json(name = "artworkUrl100")
    val artwork: String,
    val genre: String?,
    val url: String,
    val albumCopyright: String
)

