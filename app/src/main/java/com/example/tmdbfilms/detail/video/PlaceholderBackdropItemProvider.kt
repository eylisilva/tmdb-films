package com.example.tmdbfilms.detail.video

import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.tmdbfilms.R
import com.example.tmdbfilms.home.PLACE_HOLDER_BACKDROP
import com.example.tmdbfilms.home.ProviderMultiEntity

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class PlaceholderBackdropItemProvider : BaseItemProvider<ProviderMultiEntity>() {

    override val itemViewType: Int
        get() = PLACE_HOLDER_BACKDROP

    override val layoutId: Int
        get() = R.layout.item_placeholder_backdrop

    override fun convert(helper: BaseViewHolder, item: ProviderMultiEntity) {
        val placeholderBackdropIv = helper.getView<ImageView>(R.id.iv_placeholder_backdrop)
        placeholderBackdropIv.load("${IMAGE_BASE_URL}${(item as PlaceHolderBackdropMultiEntity).backdropPath}")
    }

}