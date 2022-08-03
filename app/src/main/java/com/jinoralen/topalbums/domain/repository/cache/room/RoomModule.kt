package com.jinoralen.topalbums.domain.repository.cache.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideRoomDb(
        @ApplicationContext context: Context,
    ): TopAlbumsDatabase = Room
        //.inMemoryDatabaseBuilder(context, TopAlbumsDatabase::class.java)
        .databaseBuilder(context, TopAlbumsDatabase::class.java, "appDb")
        .build()

    @Provides
    fun providePostDao(
        db: TopAlbumsDatabase
    ) = db.albumDao()
}
