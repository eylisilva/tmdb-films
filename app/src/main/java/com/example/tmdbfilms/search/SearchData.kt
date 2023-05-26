package com.example.tmdbfilms.search

data class SearchData(
    val id: Int,
    val backdropPath: String?,
    val mediaType: String,
    val releaseDate: String?,
    val title: String,
    val rating: Double,
)

val SearchData.typeAndYear: String
    get() = if (releaseDate == null || releaseDate.isEmpty()) {
        mediaType.uppercase()
    } else {
        "${mediaType.uppercase()}(${releaseDate.split("-")[0]})"
    }
