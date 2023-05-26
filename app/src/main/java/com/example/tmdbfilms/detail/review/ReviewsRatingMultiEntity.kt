package com.example.tmdbfilms.detail.review

import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.home.REVIEWS_RATING

class ReviewsRatingMultiEntity(val rating: String): ProviderMultiEntity {

    override val itemType: Int
        get() = REVIEWS_RATING

}