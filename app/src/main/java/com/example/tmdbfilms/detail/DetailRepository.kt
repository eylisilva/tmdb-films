package com.example.tmdbfilms.detail

import com.example.tmdbfilms.network.UscFilmsApiService

class DetailRepository(private val apiService: UscFilmsApiService) : IDetailRepository {

    override suspend fun getMovieDetails(movieId: Int): DetailData {
        val apiModel = apiService.getMovieDetails(movieId)
        return DetailData(
            title = apiModel.title,
            genres = apiModel.genres.map { it.name }.joinToString { it },
            year = apiModel.releaseDate.split("-")[0],
            overview = apiModel.overview,
            backdropPath = apiModel.backdropPath,
            posterPath = apiModel.posterPath
        )
    }

    override suspend fun getTvDetails(tvId: Int): DetailData {
        val apiModel = apiService.getTvDetails(tvId)
        return DetailData(
            title = apiModel.name,
            genres = apiModel.genres.map { it.name }.joinToString { it },
            year = apiModel.firstAirDate.split("-")[0],
            overview = apiModel.overview,
            backdropPath = apiModel.backdropPath,
            posterPath = apiModel.posterPath
        )
    }

}