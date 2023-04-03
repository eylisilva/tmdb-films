package com.example.tmdbfilms.detail

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_TEXT
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailTextItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_TEXT

    override val layoutId: Int
        get() = R.layout.item_detail_text

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val detailTextTv = helper.getView<TextView>(R.id.tv_detail_text)
        detailTextTv.text = (item as DetailTextMultiEntity).text
    }

}