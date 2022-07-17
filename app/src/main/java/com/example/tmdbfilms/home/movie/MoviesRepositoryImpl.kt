package com.example.tmdbfilms.home.movie

import com.example.tmdbfilms.home.SliderImageData
import com.example.tmdbfilms.network.UscFilmsApiService

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl(private val apiService: UscFilmsApiService) : MoviesRepository {

    override suspend fun getNowPlayingMovies(): List<SliderImageData> {
        val resultsApiModel = apiService.getNowPlayingMovies()
        val items = mutableListOf<SliderImageData>()
        resultsApiModel.results.take(6).forEach {
            items.add(SliderImageData(it.id, it.posterPath))
        }
        return items
    }

}