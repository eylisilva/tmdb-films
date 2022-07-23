package com.example.tmdbfilms.home


data class HorizontalScrollMultiEntity(val items: List<CardData>) : ProviderMultiEntity {

    override val itemType: Int
        get() = HORIZONTAL_SCROLL

}