package com.jinoralen.topalbums.domain.model

import com.jinoralen.topalbums.domain.di.DomainModule
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDate


class TopAlbumsResponseTests {
    @Test
    fun `should deserialize required JSON fields`() {
        val adapter = DomainModule.provideMoshi().adapter(TopAlbumsResponse::class.java)

        val jsonResponse = """
            {
                "feed": {
                    "copyright": "Copyright © 2022 Apple Inc. All rights reserved.",
                    "results": [
                        {
                            "artistName": "Beyoncé",
                            "id": "1636789969",
                            "name": "RENAISSANCE",
                            "releaseDate": "2022-07-29",
                            "artworkUrl100": "www.example.com/art1.jpg",
                            "genres": [
                                {
                                    "name": "Pop"
                                },
                                {
                                    "name": "Music"
                                }
                            ],
                            "url": "https://music.apple.com/us/album/renaissance/1636789969"
                        },
                        {
                            "artistName": "Bad Bunny",
                            "id": "1622045624",
                            "name": "Un Verano Sin Ti",
                            "releaseDate": "2022-05-06",
                            "artworkUrl100": "www.example.com/art2.jpg",
                            "genres": [
                                {
                                    "name": "Latin"
                                },
                                {
                                    "name": "Music"
                                }
                            ],
                            "url": "https://music.apple.com/us/album/un-verano-sin-ti/1622045624"
                        }
                    ]
                }
            }
        """.trimIndent()

        val expectedResponse = TopAlbumsResponse(
            feed = FeedResponse(
                results = listOf(
                    AlbumResponse(
                        id = 1636789969,
                        artistName = "Beyoncé",
                        name = "RENAISSANCE",
                        releaseDate = LocalDate.of(2022, 7, 29),
                        artwork = "www.example.com/art1.jpg",
                        genres = listOf(
                            GenreResponse(name = "Pop"),
                            GenreResponse(name = "Music")
                        ),
                        url = "https://music.apple.com/us/album/renaissance/1636789969"
                    ),
                    AlbumResponse(
                        id = 1622045624,
                        artistName = "Bad Bunny",
                        name = "Un Verano Sin Ti",
                        releaseDate = LocalDate.of(2022, 5,6),
                        artwork = "www.example.com/art2.jpg",
                        genres = listOf(
                            GenreResponse(name = "Latin"),
                            GenreResponse(name = "Music")
                        ),
                        url = "https://music.apple.com/us/album/un-verano-sin-ti/1622045624"
                    ),
                ),
                copyright = "Copyright © 2022 Apple Inc. All rights reserved."
            )
        )

        expectedResponse shouldBe adapter.fromJson(jsonResponse)
    }
}
