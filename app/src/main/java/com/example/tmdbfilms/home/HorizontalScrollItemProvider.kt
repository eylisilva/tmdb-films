package com.example.tmdbfilms.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R

class HorizontalScrollItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = HORIZONTAL_SCROLL
    override val layoutId: Int
        get() = R.layout.item_horizontal_scroll

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val items = (item as? HorizontalScrollMultiEntity)?.items
        val horizontalScrollRv = helper.getView<RecyclerView>(R.id.rv_horizontal_scroll)
        horizontalScrollRv.layoutManager = LinearLayoutManager(context).apply {
            orientation =  LinearLayoutManager.HORIZONTAL
        }
        val adapter = HorizontalScrollAdapter()
        horizontalScrollRv.adapter = adapter
        adapter.setList(items)
    }

}