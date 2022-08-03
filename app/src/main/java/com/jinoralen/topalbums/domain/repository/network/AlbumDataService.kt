package com.jinoralen.topalbums.domain.repository.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.jinoralen.topalbums.domain.model.TopAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumDataService {
    @GET("us/music/most-played/{count}/albums.json")
    suspend fun getTopAlbums(@Path("count") count: Int = 100): Either<CallError, TopAlbumsResponse>
}
