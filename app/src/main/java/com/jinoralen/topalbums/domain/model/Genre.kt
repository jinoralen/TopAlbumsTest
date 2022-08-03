package com.jinoralen.topalbums.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    val name: String
)
