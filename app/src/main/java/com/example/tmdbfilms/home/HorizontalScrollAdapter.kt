package com.example.tmdbfilms.home

import android.content.Intent
import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.detail.DetailActivity

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
private const val TMDB_URI = "https://www.themoviedb.org/"
private const val FACEBOOK_URI =
    "https://www.facebook.com/sharer/sharer.php?u=https://www.themoviedb.org/"
private const val TWITTER_URI =
    "https://twitter.com/intent/tweet?text=Check this out!%0Ahttps://www.themoviedb.org/"

class HorizontalScrollAdapter :
    BaseQuickAdapter<CardUiState, BaseViewHolder>(R.layout.item_horizontal_scroll_card) {

    override fun convert(holder: BaseViewHolder, item: CardUiState) {
        val posterIv = holder.getView<ImageView>(R.id.iv_poster)
        posterIv.load("${IMAGE_BASE_URL}${item.posterPath}")
        posterIv.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", item.id)
            val mediaType = when (item.type) {
                "movie" -> 1
                "tv" -> 2
                else -> 1
            }
            intent.putExtra("media_type", mediaType)
            context.startActivity(intent)
        }
        val btnMenuIv = holder.getView<ImageButton>(R.id.iv_btn_menu)
        btnMenuIv.setOnClickListener {
            val popupMenu = PopupMenu(context, btnMenuIv)
            popupMenu.menuInflater.inflate(R.menu.popup, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.open_in_tmdb -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("${TMDB_URI}${item.type}/${item.id}")
                        )
                        context.startActivity(intent)
                        true
                    }
                    R.id.share_on_facebook -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("${FACEBOOK_URI}${item.type}/${item.id}&amp;src=sdkpreparse")
                        )
                        context.startActivity(intent)
                        true
                    }
                    R.id.share_on_twitter -> {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("${TWITTER_URI}${item.type}/${item.id}")
                        )
                        context.startActivity(intent)
                        true
                    }
                    R.id.add_to_watchlist -> {
                        if (item.addedToWatchList) {
                            item.onRemoveFromWatchList.invoke()
                            setData(data.indexOf(item), item.copy(addedToWatchList = false))
                        } else {
                            item.onAddToWatchList.invoke()
                            setData(data.indexOf(item), item.copy(addedToWatchList = true))
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            if (item.addedToWatchList) {
                popupMenu.menu.getItem(3).title = context.getString(R.string.remove_from_watch_list)
            } else {
                popupMenu.menu.getItem(3).title = context.getString(R.string.add_to_watchlist)
            }
            popupMenu.show()
        }
    }

}