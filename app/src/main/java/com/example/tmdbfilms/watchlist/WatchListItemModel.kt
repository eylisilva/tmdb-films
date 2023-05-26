package com.example.tmdbfilms.watchlist

import com.squareup.moshi.Json

data class WatchListItemModel(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "media_type") val mediaType: String,
)