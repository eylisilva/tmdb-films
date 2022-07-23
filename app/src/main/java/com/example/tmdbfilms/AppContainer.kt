package com.example.tmdbfilms

import android.content.Context
import com.example.tmdbfilms.home.movie.MoviesRepository
import com.example.tmdbfilms.home.movie.MoviesRepositoryImpl
import com.example.tmdbfilms.home.tvshows.TvShowsRepository
import com.example.tmdbfilms.home.tvshows.TvShowsRepositoryImpl
import com.example.tmdbfilms.network.UscFilmsApi
import com.example.tmdbfilms.search.SearchRepository
import com.example.tmdbfilms.search.SearchRepositoryImpl

interface AppContainer {
    val moviesRepository: MoviesRepository
    val tvShowsRepository: TvShowsRepository
    val searchRepository: SearchRepository
}

class AppContainerImpl(private val applicationContext: Context): AppContainer {

    override val moviesRepository: MoviesRepository by lazy {
        MoviesRepositoryImpl(UscFilmsApi.retrofitService)
    }

    override val tvShowsRepository: TvShowsRepository by lazy {
        TvShowsRepositoryImpl(UscFilmsApi.retrofitService)
    }
    override val searchRepository: SearchRepository
        get() = SearchRepositoryImpl(UscFilmsApi.retrofitService)

}