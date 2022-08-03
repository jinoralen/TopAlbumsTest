package com.jinoralen.topalbums.domain.model

import com.jinoralen.topalbums.core.json.DateString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class Album(
    val id: Long,
    val artistName: String,
    val name: String,
    @DateString
    val releaseDate: LocalDate,
    @Json(name = "artworkUrl100")
    val artwork: String,
    val genres: List<Genre>,
    val url: String
)

