package com.jinoralen.topalbums.core.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
internal annotation class DateString

class DateAdapter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)

    @ToJson
    fun toJson(@DateString date: LocalDate): String? {
        return date.format(formatter)
    }

    @FromJson
    @DateString
    fun fromJson(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}
