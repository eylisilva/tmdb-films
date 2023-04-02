package com.example.tmdbfilms.detail.name

import com.example.tmdbfilms.home.DETAIL_NAME
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailNameMultiEntity(val name: String): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_NAME

}