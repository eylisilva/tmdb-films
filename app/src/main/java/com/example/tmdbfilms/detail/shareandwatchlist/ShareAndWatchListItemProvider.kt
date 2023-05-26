package com.example.tmdbfilms.detail.shareandwatchlist

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.DETAIL_SHARE_WATCHLIST
import com.example.tmdbfilms.home.ProviderMultiEntity

private const val FACEBOOK_URI =
    "https://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/"
private const val TWITTER_URI =
    "https://twitter.com/intent/tweet?text=Check this out!%0Ahttps://www.themoviedb.org/"

class ShareAndWatchListItemProvider: BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = DETAIL_SHARE_WATCHLIST

    override val layoutId: Int
        get() = R.layout.item_detail_share_watchlist

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val addedToWatchList = (item as DetailShareAndWatchListItemMultiEntity).addedToWatchList
        val addRemoveIv = helper.getView<ImageView>(R.id.iv_add_remove)
        if (addedToWatchList) {
            addRemoveIv.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24)
        } else {
            addRemoveIv.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
        }
        val adapter = getAdapter()
        val index = adapter?.data?.indexOf(item)
        addRemoveIv.setOnClickListener {
            if (addedToWatchList) {
                item.onRemoveFromWatchList.invoke()
                if (index != null) {
                    adapter.setData(
                        index,
                        DetailShareAndWatchListItemMultiEntity(
                            id = item.id,
                            type = item.type,
                            addedToWatchList = false,
                            onAddToWatchList = item.onAddToWatchList,
                            onRemoveFromWatchList = item.onRemoveFromWatchList
                        )
                    )
                }
            } else {
                item.onAddToWatchList.invoke()
                if (index != null) {
                    adapter.setData(
                        index,
                        DetailShareAndWatchListItemMultiEntity(
                            id = item.id,
                            type = item.type,
                            addedToWatchList = true,
                            onAddToWatchList = item.onAddToWatchList,
                            onRemoveFromWatchList = item.onRemoveFromWatchList
                        )
                    )
                }
            }
        }
        val facebookIv = helper.getView<ImageView>(R.id.iv_facebook)
        facebookIv.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$FACEBOOK_URI${item.type}/${item.id}&amp;src=sdkpreparse")
            )
            context.startActivity(intent)
        }
        val twitterIv = helper.getView<ImageView>(R.id.iv_twitter)
        twitterIv.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("${TWITTER_URI}${item.type}/${item.id}")
            )
            context.startActivity(intent)
        }
    }
}