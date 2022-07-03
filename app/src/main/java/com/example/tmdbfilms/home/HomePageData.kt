package com.example.tmdbfilms.home

data class HomePageData(
    val success: Boolean,
    val movieSliderItems: List<SliderImageItem>? = null,
    val tvShowSliderItems: List<SliderImageItem>? = null
)