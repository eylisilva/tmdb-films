package com.example.tmdbfilms.home


data class HorizontalScrollMultiEntity(val items: List<CardUiState>) : ProviderMultiEntity {

    override val itemType: Int
        get() = HORIZONTAL_SCROLL

}