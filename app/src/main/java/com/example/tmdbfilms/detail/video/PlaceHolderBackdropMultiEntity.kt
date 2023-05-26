package com.example.tmdbfilms.detail.video

import com.example.tmdbfilms.home.PLACE_HOLDER_BACKDROP
import com.example.tmdbfilms.home.ProviderMultiEntity

data class PlaceHolderBackdropMultiEntity(val backdropPath: String): ProviderMultiEntity {

    override val itemType: Int
        get() = PLACE_HOLDER_BACKDROP

}
