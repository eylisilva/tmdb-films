package com.example.tmdbfilms.home

const val SLIDER = 1
const val HEADER = 2
const val HORIZONTAL_SCROLL = 3
const val TRAILER = 4
const val PLACE_HOLDER_BACKDROP = 5
const val DETAIL_NAME = 6
const val DETAIL_SUBTITLE = 7

interface ProviderMultiEntity {

    val itemType: Int

}