package com.example.tmdbfilms.detail.overview

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_OVERVIEW
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailOverviewItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_OVERVIEW

    override val layoutId: Int
        get() = R.layout.item_detail_overview

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val readMoreTextView = helper.getView<ReadMoreTextView>(R.id.tv_overview)
        readMoreTextView.text = (item as DetailOverviewMultiEntity).overview
    }

}