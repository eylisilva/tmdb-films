package com.example.tmdbfilms.search

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.detail.DetailActivity
import java.text.DecimalFormat

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class SearchResultAdapter :
    BaseQuickAdapter<SearchData, BaseViewHolder>(R.layout.item_search_result) {

    private val decimalFormat = DecimalFormat("#.#")

    override fun convert(holder: BaseViewHolder, item: SearchData) {
        val typeAndYearTv = holder.getView<TextView>(R.id.tv_type_and_year)
        val ratingTv = holder.getView<TextView>(R.id.tv_rating)
        val titleTv = holder.getView<TextView>(R.id.tv_title)
        val backdropIv = holder.getView<ImageView>(R.id.iv_backdrop)
        typeAndYearTv.text = item.typeAndYear
        ratingTv.text = decimalFormat.format(item.rating)
        titleTv.text = item.title
        if (item.backdropPath.isNullOrEmpty().not()) {
            backdropIv.load("${IMAGE_BASE_URL}${item.backdropPath}")
        }
        backdropIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", item.id)
            val mediaType = when (item.mediaType) {
                "movie" -> 1
                "tv" -> 2
                else -> 1
            }
            intent.putExtra("media_type", mediaType)
            context.startActivity(intent)
        }
    }

}