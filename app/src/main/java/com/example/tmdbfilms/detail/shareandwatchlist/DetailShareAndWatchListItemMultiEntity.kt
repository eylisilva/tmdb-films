package com.example.tmdbfilms.detail.shareandwatchlist

import com.example.tmdbfilms.home.DETAIL_SHARE_WATCHLIST
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailShareAndWatchListItemMultiEntity(
    val id: Int,
    val type: String,
    val addedToWatchList: Boolean,
    val onAddToWatchList: () -> Unit,
    val onRemoveFromWatchList: () -> Unit
) : ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_SHARE_WATCHLIST

}