package com.jinoralen.topalbums.domain.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.jinoralen.topalbums.core.json.DateAdapter
import com.jinoralen.topalbums.domain.repository.network.AlbumDataService
import com.jinoralen.topalbums.domain.repository.network.AlbumsRepository
import com.jinoralen.topalbums.domain.repository.network.RemoteAlbumsRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    companion object {
        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(DateAdapter())
            .build()

        @Provides
        @Singleton
        fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            moshi: Moshi,
        ): Retrofit = Retrofit.Builder()
            .baseUrl("https://rss.applemarketingtools.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(EitherCallAdapterFactory())
            .client(okHttpClient)
            .build()

        @Provides
        fun provideService(retrofit: Retrofit): AlbumDataService = retrofit.create(AlbumDataService::class.java)
    }

    @Binds
    abstract fun provideRemoteRepository(albumsRepository: RemoteAlbumsRepositoryImpl): AlbumsRepository
}
