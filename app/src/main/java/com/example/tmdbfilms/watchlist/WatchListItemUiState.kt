package com.example.tmdbfilms.watchlist

data class WatchListItemUiState(
    val id: Int,
    val title: String,
    val posterPath: String,
    val mediaType: String,
    val onRemoveClick: () -> Unit
)
