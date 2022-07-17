package com.example.tmdbfilms.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.utils.ViewUtils

private const val CARD_MARGIN = 10

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
        horizontalScrollRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view)
                if (position > 0) {
                    outRect.left = ViewUtils.dp2px(context, CARD_MARGIN)
                }
            }
        })
        val adapter = HorizontalScrollAdapter()
        horizontalScrollRv.adapter = adapter
        adapter.setList(items)
    }

}