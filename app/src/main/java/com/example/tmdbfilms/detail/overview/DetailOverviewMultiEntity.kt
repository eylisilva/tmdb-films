package com.example.tmdbfilms.detail.overview

import com.example.tmdbfilms.home.DETAIL_OVERVIEW
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailOverviewMultiEntity(val overview: String): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_OVERVIEW

}