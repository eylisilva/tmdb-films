package com.example.tmdbfilms.home

import com.example.tmdbfilms.home.movie.MoviesRepository
import com.example.tmdbfilms.home.tvshows.TvShowsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetHomePageDataUseCase(
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository
) {
    suspend fun getHomePageData(): HomePageData {
        return coroutineScope {
            val movieSliderImageItems = async {
                moviesRepository.getNowPlayingMovies()
            }
            val tvShowSliderImageItems = async {
                tvShowsRepository.getTrendingTvShows()
            }
            HomePageData(
                movieSliderItems = movieSliderImageItems.await(),
                tvShowSliderItems = tvShowSliderImageItems.await()
            )
        }
    }
}