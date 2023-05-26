package com.example.tmdbfilms.detail.review

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.example.tmdbfilms.detail.DetailSubtitleItemProvider
import com.example.tmdbfilms.detail.DetailTextItemProvider
import com.example.tmdbfilms.home.ProviderMultiEntity

class ReviewsProviderMultiAdapter: BaseProviderMultiAdapter<ProviderMultiEntity>() {

    init {
        addItemProvider(ReviewsRatingItemProvider())
        addItemProvider(DetailSubtitleItemProvider())
        addItemProvider(DetailTextItemProvider())
    }

    override fun getItemType(data: List<ProviderMultiEntity>, position: Int): Int {
        return data[position].itemType
    }

}