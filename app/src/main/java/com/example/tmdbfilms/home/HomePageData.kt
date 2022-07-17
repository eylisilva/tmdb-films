package com.example.tmdbfilms.home

data class HomePageData(
    val movieSliderItems: List<SliderImageData>? = null,
    val tvShowSliderItems: List<SliderImageData>? = null,
    val topRatedMovieItems: List<CardData>? = null,
    val topRatedTvShowItems: List<CardData>? = null
)