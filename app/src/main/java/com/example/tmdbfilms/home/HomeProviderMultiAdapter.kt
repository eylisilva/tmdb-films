package com.example.tmdbfilms.home

import com.chad.library.adapter.base.BaseProviderMultiAdapter

class HomeProviderMultiAdapter: BaseProviderMultiAdapter<ProviderMultiEntity>() {

    init {
        addItemProvider(SliderItemProvider())
        addItemProvider(HeaderItemProvider())
    }

    override fun getItemType(data: List<ProviderMultiEntity>, position: Int): Int {
        return data[position].itemType
    }

}