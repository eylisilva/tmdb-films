package com.example.tmdbfilms.home.movie

import com.example.tmdbfilms.home.CardData
import com.example.tmdbfilms.home.SliderImageData

interface MoviesRepository {

    suspend fun getNowPlayingMovies(): List<SliderImageData>

    suspend fun getPopularMovies(): List<CardData>

}