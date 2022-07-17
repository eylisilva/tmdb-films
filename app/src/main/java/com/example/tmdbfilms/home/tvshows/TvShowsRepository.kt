package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.CardData
import com.example.tmdbfilms.home.SliderImageData

interface TvShowsRepository {

    suspend fun getTrendingTvShows(): List<SliderImageData>

    suspend fun getTopRatedTvShows(): List<CardData>

}