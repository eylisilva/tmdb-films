package com.example.tmdbfilms.home.movie

import com.example.tmdbfilms.home.SliderImageItem

interface MoviesRepository {
    suspend fun getNowPlayingMovies(): List<SliderImageItem>
}