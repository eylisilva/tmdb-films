package com.example.tmdbfilms.detail.actor

import com.example.tmdbfilms.home.DETAIL_CAST
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailCastMultiEntity(val actors: List<ActorData>): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_CAST

}