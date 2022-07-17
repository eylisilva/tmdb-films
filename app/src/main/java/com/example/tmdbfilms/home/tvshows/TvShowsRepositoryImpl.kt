package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.SliderImageData
import com.example.tmdbfilms.network.UscFilmsApiService

class TvShowsRepositoryImpl(private val apiService: UscFilmsApiService) : TvShowsRepository {

    override suspend fun getTrendingTvShows(): List<SliderImageData> {
        val resultsApiModel = apiService.getTrendingTvShows()
        val items = mutableListOf<SliderImageData>()
        resultsApiModel.results.forEach {
            items.add(SliderImageData(it.id, it.posterPath))
        }
        return items
    }

}