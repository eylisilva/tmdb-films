package com.example.tmdbfilms.detail

import com.squareup.moshi.Json

data class MovieDetailApiModel(
    val title: String,
    val genres: List<Genre>,
    @Json(name = "release_date") val releaseDate: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String
)