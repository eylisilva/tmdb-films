package com.example.tmdbfilms.home

data class HomePageData(
    val movieSliderItems: List<SliderImageData>? = null,
    val tvShowSliderItems: List<SliderImageData>? = null
)