package com.example.tmdbfilms.detail.actor

import com.squareup.moshi.Json

data class Actor(
    val name: String,
    @Json(name = "profile_path") val profilePath: String
)
