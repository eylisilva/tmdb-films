package com.example.tmdbfilms.detail.recommended

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_RECOMMENDED
import com.example.tmdbfilms.home.ProviderMultiEntity

class RecommendedPicksItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_RECOMMENDED

    override val layoutId: Int
        get() = R.layout.item_horizontal_scroll

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val items = (item as? RecommendedPicksScrollMultiEntity)?.items
        val horizontalScrollRv = helper.getView<RecyclerView>(R.id.rv_horizontal_scroll)
        horizontalScrollRv.layoutManager = LinearLayoutManager(context).apply {
            orientation =  LinearLayoutManager.HORIZONTAL
        }
        val adapter = RecommendedPicksScrollAdapter()
        horizontalScrollRv.adapter = adapter
        adapter.setList(items)
    }

}