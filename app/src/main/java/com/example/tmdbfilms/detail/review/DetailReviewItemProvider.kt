package com.example.tmdbfilms.detail.review

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_REVIEW
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailReviewItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_REVIEW

    override val layoutId: Int
        get() = R.layout.item_detail_review

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val authorAndDateTv = helper.getView<TextView>(R.id.tv_author_and_date)
        authorAndDateTv.text = (item as ReviewMultiEntity).reviewData.title
        val ratingTv = helper.getView<TextView>(R.id.tv_rating)
        ratingTv.text = item.reviewData.rating
        val reviewContentTv = helper.getView<TextView>(R.id.tv_review_content)
        reviewContentTv.text = item.reviewData.content
    }

}