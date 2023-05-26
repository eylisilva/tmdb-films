package com.example.tmdbfilms.detail.video

import androidx.lifecycle.Lifecycle
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.ProviderMultiEntity
import com.example.tmdbfilms.home.TRAILER
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailerItemProvider(private val lifecycle: Lifecycle) :
    BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = TRAILER

    override val layoutId: Int
        get() = R.layout.item_trailer

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val playerView = helper.getView<YouTubePlayerView>(R.id.youtube_player_view)
        lifecycle.addObserver(playerView)
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo((item as TrailerMultiEntity).videoKey, 0f)
            }
        })
    }

}