package com.example.tmdbfilms.home.movie

import com.example.tmdbfilms.home.SliderImageItem
import com.example.tmdbfilms.network.UscFilmsApiService

class MoviesRepositoryImpl(private val apiService: UscFilmsApiService) : MoviesRepository {

    override suspend fun getNowPlayingMovies(): List<SliderImageItem> {
        val resultsApiModel = apiService.getNowPlayingMovies()
        val items = mutableListOf<SliderImageItem>()
        resultsApiModel.results.forEach {
            items.add(SliderImageItem(it.id, it.posterPath))
        }
        return items
    }

}