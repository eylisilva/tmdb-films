package com.example.tmdbfilms.detail

import com.example.tmdbfilms.home.DETAIL_SUBTITLE
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailSubtitleMultiEntity(val subtitle: String) : ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_SUBTITLE

}