package com.example.tmdbfilms.watchlist

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.detail.DetailActivity

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class WatchListAdapter(
    private val onRowMovedCallback: (Int, Int) -> Unit
) :
    BaseQuickAdapter<WatchListItemUiState, BaseViewHolder>(R.layout.item_watch_list),
    ItemMoveCallback.ItemTouchHelperContract {

    override fun convert(holder: BaseViewHolder, item: WatchListItemUiState) {
        val posterIv = holder.getView<ImageView>(R.id.iv_poster)
        posterIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", item.id)
            val mediaType = when (item.mediaType) {
                "movie" -> 1
                "tv" -> 2
                else -> 1
            }
            intent.putExtra("media_type", mediaType)
            context.startActivity(intent)
        }
        val mediaTypeTv = holder.getView<TextView>(R.id.tv_media_type)
        val removeIv = holder.getView<ImageView>(R.id.iv_remove)
        posterIv.load("$IMAGE_BASE_URL${item.posterPath}")
        mediaTypeTv.text = item.mediaType
        removeIv.setOnClickListener {
            item.onRemoveClick.invoke()
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        onRowMovedCallback.invoke(fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

}