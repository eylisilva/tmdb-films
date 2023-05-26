package com.example.tmdbfilms.detail

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_SUBTITLE
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailSubtitleItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_SUBTITLE

    override val layoutId: Int
        get() = R.layout.item_detail_subtitle

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val subtitleTv = helper.getView<TextView>(R.id.tv_detail_subtitle)
        subtitleTv.text = (item as DetailSubtitleMultiEntity).subtitle
    }

}
