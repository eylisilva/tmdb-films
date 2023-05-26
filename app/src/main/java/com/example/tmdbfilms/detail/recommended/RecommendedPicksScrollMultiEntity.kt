package com.example.tmdbfilms.detail.recommended

import com.example.tmdbfilms.home.DETAIL_RECOMMENDED
import com.example.tmdbfilms.home.ProviderMultiEntity

class RecommendedPicksScrollMultiEntity(val items: List<RecommendedCardUiState>): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_RECOMMENDED

}