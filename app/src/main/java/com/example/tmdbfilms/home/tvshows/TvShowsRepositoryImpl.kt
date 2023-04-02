package com.example.tmdbfilms.home.tvshows

import com.example.tmdbfilms.home.CardData
import com.example.tmdbfilms.home.SliderImageData
import com.example.tmdbfilms.network.UscFilmsApiService

private const val TYPE_TV = "tv"

class TvShowsRepositoryImpl(private val apiService: UscFilmsApiService) : TvShowsRepository {

    override suspend fun getTrendingTvShows(): List<SliderImageData> {
        val resultsApiModel = apiService.getTrendingTvShows()
        val items = mutableListOf<SliderImageData>()
        resultsApiModel.results.take(6).forEach {
            items.add(SliderImageData(it.id, it.posterPath, TYPE_TV))
        }
        return items
    }

    override suspend fun getTopRatedTvShows(): List<CardData> {
        val resultsApiModel = apiService.getTopRatedTvShows()
        val items = mutableListOf<CardData>()
        resultsApiModel.results.take(10).forEach {
            items.add(CardData(it.id, it.posterPath, TYPE_TV, it.name))
        }
        return items
    }

    override suspend fun getPopularTvShows(): List<CardData> {
        val resultsApiModel = apiService.getPopularTvShows()
        val items = mutableListOf<CardData>()
        resultsApiModel.results.take(10).forEach {
            items.add(CardData(it.id, it.posterPath, TYPE_TV, it.name))
        }
        return items
    }

    override suspend fun getTvRecommendations(tvId: Int): List<CardData> {
        val resultsApiModel = apiService.getTvRecommendations(tvId)
        val items = mutableListOf<CardData>()
        resultsApiModel.results.take(10).forEach {
            items.add(CardData(it.id, it.posterPath, TYPE_TV, it.name))
        }
        return items
    }
}