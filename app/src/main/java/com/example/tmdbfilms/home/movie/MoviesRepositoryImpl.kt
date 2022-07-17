package com.example.tmdbfilms.home.movie

import android.util.Log
import com.example.tmdbfilms.home.CardData
import com.example.tmdbfilms.home.SliderImageData
import com.example.tmdbfilms.network.UscFilmsApiService

private const val TAG = "MoviesRepositoryImpl"
private const val TYPE_MOVIE = "movie"

class MoviesRepositoryImpl(private val apiService: UscFilmsApiService) : MoviesRepository {

    override suspend fun getNowPlayingMovies(): List<SliderImageData> {
        val resultsApiModel = apiService.getNowPlayingMovies()
        val items = mutableListOf<SliderImageData>()
        Log.i(TAG, "getPopularMovies size: ${items.size}")
        resultsApiModel.results.take(6).forEach {
            items.add(SliderImageData(it.id, it.posterPath))
        }
        return items
    }

    override suspend fun getPopularMovies(): List<CardData> {
        val resultsApiModel = apiService.getPopularMovies()
        val items = mutableListOf<CardData>()
        Log.i(TAG, "getPopularMovies size: ${items.size}")
        resultsApiModel.results.take(10).forEach {
            items.add(CardData(it.id, it.posterPath, TYPE_MOVIE))
        }
        return items
    }


}