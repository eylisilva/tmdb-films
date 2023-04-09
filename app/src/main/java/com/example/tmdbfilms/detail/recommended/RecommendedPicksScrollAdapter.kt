package com.example.tmdbfilms.detail.recommended

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class RecommendedPicksScrollAdapter :
    BaseQuickAdapter<RecommendedCardUiState, BaseViewHolder>(R.layout.item_recommended_pick) {

    override fun convert(holder: BaseViewHolder, item: RecommendedCardUiState) {
        val posterIv = holder.getView<ImageView>(R.id.iv_poster)
        posterIv.load("$IMAGE_BASE_URL${item.posterPath}")
    }

}