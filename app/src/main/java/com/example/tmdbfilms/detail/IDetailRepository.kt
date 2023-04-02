package com.example.tmdbfilms.detail

interface IDetailRepository {

    suspend fun getMovieDetails(movieId: Int): DetailData

    suspend fun getTvDetails(tvId: Int): DetailData

}