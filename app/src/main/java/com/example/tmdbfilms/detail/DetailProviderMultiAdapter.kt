package com.example.tmdbfilms.detail

import android.app.Activity
import androidx.lifecycle.Lifecycle
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.example.tmdbfilms.detail.video.TrailerItemProvider
import com.example.tmdbfilms.home.ProviderMultiEntity

class DetailProviderMultiAdapter(private val lifecycle: Lifecycle): BaseProviderMultiAdapter<ProviderMultiEntity>() {

    init {
        addItemProvider(TrailerItemProvider(lifecycle))
    }

    override fun getItemType(data: List<ProviderMultiEntity>, position: Int): Int {
        return data[position].itemType
    }

}

// class HomeProviderMultiAdapter: BaseProviderMultiAdapter<ProviderMultiEntity>() {
//
//    init {
//        addItemProvider(SliderItemProvider())
//        addItemProvider(HeaderItemProvider())
//        addItemProvider(HorizontalScrollItemProvider())
//    }
//
//    override fun getItemType(data: List<ProviderMultiEntity>, position: Int): Int {
//        return data[position].itemType
//    }
//
//}