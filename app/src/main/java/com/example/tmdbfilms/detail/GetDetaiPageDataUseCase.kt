package com.example.tmdbfilms.detail

import com.example.tmdbfilms.detail.review.IReviewRepository
import com.example.tmdbfilms.detail.video.IVideoRepository
import com.example.tmdbfilms.detail.video.VideoRepository
import com.example.tmdbfilms.home.movie.MoviesRepository
import com.example.tmdbfilms.home.tvshows.TvShowsRepository
import com.example.tmdbfilms.watchlist.WatchListRepository
import kotlinx.coroutines.coroutineScope

const val TYPE_MOVIE = 1
const val TYPE_TV = 2

class GetDetailPageDataUseCase(
    private val videoRepository: IVideoRepository,
    private val detailRepository: IDetailRepository,
    private val watchListRepository: WatchListRepository,
    private val reviewRepository: IReviewRepository,
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository
) {

    suspend fun getDetailPageData(id: Int, type: Int): DetailPageData {
        return coroutineScope {
            val detailData = when (type) {
                TYPE_MOVIE -> detailRepository.getMovieDetails(id)
                else -> detailRepository.getTvDetails(id)
            }
            val reviews = when (type) {
                TYPE_MOVIE -> reviewRepository.getMovieReviews(id)
                else -> reviewRepository.getTvReviews(id)
            }
            val recommendations = when (type) {
                TYPE_MOVIE -> moviesRepository.getMovieRecommendations(id)
                else -> tvShowsRepository.getTvRecommendations(id)
            }
            val videoKey = when (type) {
                TYPE_MOVIE -> videoRepository.getMovieVideoKey(id)
                else -> videoRepository.getTvVideoKey(id)
            }
            val addedToWatchList = watchListRepository.contains(id)
            DetailPageData(
                detailData = detailData,
                reviews = reviews,
                recommendations = recommendations,
                videoKey = videoKey,
                addedToWatchList = addedToWatchList
            )
        }
    }

}