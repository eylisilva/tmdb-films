package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.SliderImageData

interface TvShowsRepository {

    suspend fun getTrendingTvShows(): List<SliderImageData>

}