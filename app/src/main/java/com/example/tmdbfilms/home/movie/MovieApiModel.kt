package com.example.tmdbfilms.home.movie

import com.squareup.moshi.Json

data class MovieApiModel(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String
)