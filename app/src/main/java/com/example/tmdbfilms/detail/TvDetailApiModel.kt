package com.example.tmdbfilms.detail

import com.squareup.moshi.Json

data class TvDetailApiModel(
    val name: String,
    val genres: List<Genre>,
    @Json(name = "first_air_date") val firstAirDate: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String
)