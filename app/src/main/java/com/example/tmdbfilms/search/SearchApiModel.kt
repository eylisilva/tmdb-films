package com.example.tmdbfilms.search

import com.squareup.moshi.Json

data class SearchApiModel(
    val id: Int,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "media_type") val mediaType: String,
    val title: String?,
    val name: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "release_date") val releaseDate: String?
)