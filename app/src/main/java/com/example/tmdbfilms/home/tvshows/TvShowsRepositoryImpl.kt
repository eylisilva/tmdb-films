package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.SliderImageItem
import com.example.tmdbfilms.network.UscFilmsApiService

class TvShowsRepositoryImpl(private val apiService: UscFilmsApiService) : TvShowsRepository {

    override suspend fun getTrendingTvShows(): List<SliderImageItem> {
        val resultsApiModel = apiService.getTrendingTvShows()
        val items = mutableListOf<SliderImageItem>()
        resultsApiModel.results.forEach {
            items.add(SliderImageItem(it.id, it.posterPath))
        }
        return items
    }

}