package com.example.tmdbfilms.search

import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class SearchResultAdapter :
    BaseQuickAdapter<SearchData, BaseViewHolder>(R.layout.item_search_result) {

    override fun convert(holder: BaseViewHolder, item: SearchData) {
        val typeAndYearTv = holder.getView<TextView>(R.id.tv_type_and_year)
        val ratingTv = holder.getView<TextView>(R.id.tv_rating)
        val titleTv = holder.getView<TextView>(R.id.tv_title)
        val backdropIv = holder.getView<ImageView>(R.id.iv_backdrop)
        typeAndYearTv.text = item.typeAndYear
        ratingTv.text = item.rating.toString()
        titleTv.text = item.title
        if (item.backdropPath.isNullOrEmpty().not()) {
            backdropIv.load("${IMAGE_BASE_URL}${item.backdropPath}")
        }
    }

}