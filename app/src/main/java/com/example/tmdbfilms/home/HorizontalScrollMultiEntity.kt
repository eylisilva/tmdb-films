package com.example.tmdbfilms.home

import com.example.tmdbfilms.R

data class HorizontalScrollMultiEntity(val items: List<CardData>) : ProviderMultiEntity {

    override val itemType: Int
        get() = HORIZONTAL_SCROLL

}