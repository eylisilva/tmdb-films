package com.example.tmdbfilms.detail

import androidx.lifecycle.Lifecycle
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.example.tmdbfilms.detail.actor.DetailCastItemProvider
import com.example.tmdbfilms.detail.name.DetailNameItemProvider
import com.example.tmdbfilms.detail.overview.DetailOverviewItemProvider
import com.example.tmdbfilms.detail.shareandwatchlist.ShareAndWatchListItemProvider
import com.example.tmdbfilms.detail.video.PlaceholderBackdropItemProvider
import com.example.tmdbfilms.detail.video.TrailerItemProvider
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailProviderMultiAdapter(lifecycle: Lifecycle): BaseProviderMultiAdapter<ProviderMultiEntity>() {

    init {
        addItemProvider(TrailerItemProvider(lifecycle))
        addItemProvider(PlaceholderBackdropItemProvider())
        addItemProvider(DetailNameItemProvider())
        addItemProvider(DetailSubtitleItemProvider())
        addItemProvider(DetailOverviewItemProvider())
        addItemProvider(DetailTextItemProvider())
        addItemProvider(ShareAndWatchListItemProvider())
        addItemProvider(DetailCastItemProvider())
    }

    override fun getItemType(data: List<ProviderMultiEntity>, position: Int): Int {
        return data[position].itemType
    }

}
