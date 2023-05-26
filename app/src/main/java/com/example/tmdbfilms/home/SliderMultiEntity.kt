package com.example.tmdbfilms.home

data class SliderMultiEntity(val items: List<SliderImageData>) : ProviderMultiEntity {

    override val itemType: Int
        get() = SLIDER

}