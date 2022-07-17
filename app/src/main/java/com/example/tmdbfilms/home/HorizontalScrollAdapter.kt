package com.example.tmdbfilms.home

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class HorizontalScrollAdapter :
    BaseQuickAdapter<CardData, BaseViewHolder>(R.layout.item_horizontal_scroll_card) {

    override fun convert(holder: BaseViewHolder, item: CardData) {
        val posterIv = holder.getView<ImageView>(R.id.iv_poster)
        posterIv.load("${IMAGE_BASE_URL}${item.posterPath}")
    }

}