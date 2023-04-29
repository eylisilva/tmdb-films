package com.example.tmdbfilms.detail

import com.example.tmdbfilms.home.DETAIL_TEXT
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailTextMultiEntity(val text: String, val maxLines: Int = 4): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_TEXT

}