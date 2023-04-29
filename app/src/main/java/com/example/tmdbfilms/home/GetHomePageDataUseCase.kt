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
            val topRatedMovieItems = async {
                moviesRepository.getTopRatedMovies()
            }
            val popularMovieItems = async {
                moviesRepository.getPopularMovies()
            }
            val tvShowSliderImageItems = async {
                tvShowsRepository.getTrendingTvShows()
            }
            val topRatedTvShowItems = async {
                tvShowsRepository.getTopRatedTvShows()
            }
            val popularTvShowItems = async {
                tvShowsRepository.getPopularTvShows()
            }
            HomePageData(
                movieSliderItems = movieSliderImageItems.await(),
                tvShowSliderItems = tvShowSliderImageItems.await(),
                topRatedMovieItems = topRatedMovieItems.await(),
                topRatedTvShowItems = topRatedTvShowItems.await(),
                popularMovieItems = popularMovieItems.await(),
                popularTvShowItems = popularTvShowItems.await()
            )
        }
    }
}