package com.example.tmdbfilms.detail.name

import android.widget.TextView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_NAME
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailNameItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_NAME

    override val layoutId: Int
        get() = R.layout.item_detail_name

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val detailNameTv = helper.getView<TextView>(R.id.tv_detail_name)
        detailNameTv.text = (item as DetailNameMultiEntity).name
    }

}