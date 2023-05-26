package com.example.tmdbfilms.detail.video

import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.home.TRAILER

data class TrailerMultiEntity(val videoKey: String): ProviderMultiEntity {

    override val itemType: Int
        get() = TRAILER

}
