package com.example.tmdbfilms.detail

import com.example.tmdbfilms.detail.actor.ActorData
import com.example.tmdbfilms.detail.review.ReviewData
import com.example.tmdbfilms.home.CardData

data class DetailPageData(
    val detailData: DetailData,
    val reviews: List<ReviewData>,
    val recommendations: List<CardData>,
    val videoKey: String? = null,
    val addedToWatchList: Boolean,
    val actors: List<ActorData>
)
