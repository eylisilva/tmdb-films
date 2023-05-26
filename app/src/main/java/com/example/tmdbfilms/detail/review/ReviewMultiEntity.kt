package com.example.tmdbfilms.detail.review

import com.example.tmdbfilms.home.DETAIL_REVIEW
import com.example.tmdbfilms.home.ProviderMultiEntity

class ReviewMultiEntity(val reviewData: ReviewData): ProviderMultiEntity {

    override val itemType: Int
        get() = DETAIL_REVIEW

}