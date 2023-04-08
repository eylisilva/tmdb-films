package com.example.tmdbfilms.detail.actor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_CAST
import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.utils.ViewUtils

private const val ITEM_MARGIN = 16

class DetailCastItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    private val adapter by lazy {
        CastAdapter()
    }

    override val itemViewType: Int
        get() = DETAIL_CAST

    override val layoutId: Int
        get() = R.layout.item_detail_cast

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val detailCastRv = helper.getView<RecyclerView>(R.id.rv_detail_cast)
        detailCastRv.layoutManager =
            GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        detailCastRv.adapter = adapter
        adapter.setList((item as DetailCastMultiEntity).actors)
        detailCastRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.top = ViewUtils.dp2px(context, ITEM_MARGIN)
            }
        })
    }

}