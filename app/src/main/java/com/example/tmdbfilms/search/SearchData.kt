package com.example.tmdbfilms.search

data class SearchData(
    val id: Int,
    val backdropPath: String?,
    val typeAndYear: String,
    val title: String,
    val rating: Double,
)
