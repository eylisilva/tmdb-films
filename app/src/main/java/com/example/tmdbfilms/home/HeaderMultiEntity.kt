package com.example.tmdbfilms.home

data class HeaderMultiEntity(val title: String) : ProviderMultiEntity {

    override val itemType: Int
        get() = HEADER

}