package com.example.tmdbfilms.detail.review

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.home.REVIEWS_RATING

class ReviewsRatingItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = REVIEWS_RATING

    override val layoutId: Int
        get() = R.layout.item_reviews_rating

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val ratingTv = helper.getView<TextView>(R.id.tv_rating)
        ratingTv.text = (item as ReviewsRatingMultiEntity).rating
    }

}