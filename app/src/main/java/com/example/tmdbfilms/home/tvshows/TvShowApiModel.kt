package com.example.tmdbfilms.home.tvshows

import com.squareup.moshi.Json

data class TvShowApiModel(
    val id: Int,
    val name: String,
    @Json(name = "poster_path") val posterPath: String,
)