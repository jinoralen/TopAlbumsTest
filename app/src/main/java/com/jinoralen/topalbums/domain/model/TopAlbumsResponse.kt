package com.jinoralen.topalbums.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopAlbumsResponse(
    val feed: Feed
)

@JsonClass(generateAdapter = true)
data class Feed(
    val copyright: String,
    val results: List<Album>
)

