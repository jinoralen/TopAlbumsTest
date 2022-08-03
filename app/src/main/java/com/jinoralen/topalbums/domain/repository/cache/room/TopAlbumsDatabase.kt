package com.jinoralen.topalbums.domain.repository.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.jinoralen.topalbums.core.json.DateAdapter
import com.jinoralen.topalbums.domain.model.Album
import com.jinoralen.topalbums.domain.model.Genre
import java.time.LocalDate

@Database(
    entities = [Album::class],
    version = 1,
    exportSchema = false)
@TypeConverters(TopAlbumsDatabase.Converters::class)
abstract class TopAlbumsDatabase: RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    class Converters {
        private val dateAdapter = DateAdapter()

        @TypeConverter
        fun fromLocalDate(value: LocalDate?): String? {
            return dateAdapter.toJson(value)
        }

        @TypeConverter
        fun toLocalDate(value: String?): LocalDate? {
            return dateAdapter.fromJson(value)
        }

        @TypeConverter
        fun fromGenre(value: List<Genre>): String {
            return value.joinToString(DELIMITER) { it.name }
        }

        @TypeConverter
        fun toGenre(value: String): List<Genre> {
            return value.split(DELIMITER).map { Genre(it) }
        }

        companion object {
            private const val DELIMITER = ","
        }
    }

}
