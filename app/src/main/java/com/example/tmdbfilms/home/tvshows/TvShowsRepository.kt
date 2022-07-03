package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.SliderImageItem

interface TvShowsRepository {

    suspend fun getTrendingTvShows(): List<SliderImageItem>

}