package com.example.tmdbfilms.home

data class CardUiState(
    val id: Int,
    val posterPath: String,
    val type: String,
    val addedToWatchList: Boolean,
    val onAddToWatchList: () -> Unit,
    val onRemoveFromWatchList: () -> Unit
)