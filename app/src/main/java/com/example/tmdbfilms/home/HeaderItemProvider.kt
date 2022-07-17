package com.example.tmdbfilms.home

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R

class HeaderItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = HEADER

    override val layoutId: Int
        get() = R.layout.item_header

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val headerTv = helper.getView<TextView>(R.id.tv_header)
        headerTv.text = (item as? HeaderMultiEntity)?.title
    }

}