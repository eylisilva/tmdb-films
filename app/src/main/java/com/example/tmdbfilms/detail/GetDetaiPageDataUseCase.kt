package com.example.tmdbfilms.detail

import com.example.tmdbfilms.detail.actor.IActorRepository
import com.example.tmdbfilms.detail.review.IReviewRepository
import com.example.tmdbfilms.detail.video.IVideoRepository
import com.example.tmdbfilms.home.movie.MoviesRepository
import com.example.tmdbfilms.home.tvshows.TvShowsRepository
import com.example.tmdbfilms.watchlist.WatchListRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

const val TYPE_MOVIE = 1
const val TYPE_TV = 2

class GetDetailPageDataUseCase(
    private val videoRepository: IVideoRepository,
    private val detailRepository: IDetailRepository,
    private val watchListRepository: WatchListRepository,
    private val reviewRepository: IReviewRepository,
    private val moviesRepository: MoviesRepository,
    private val tvShowsRepository: TvShowsRepository,
    private val actorRepository: IActorRepository
) {

    suspend fun getDetailPageData(id: Int, type: Int): DetailPageData {
        return coroutineScope {
            val detailData = async {
                when (type) {
                    TYPE_MOVIE -> detailRepository.getMovieDetails(id)
                    else -> detailRepository.getTvDetails(id)
                }
            }
            val reviews = async {
                when (type) {
                    TYPE_MOVIE -> reviewRepository.getMovieReviews(id)
                    else -> reviewRepository.getTvReviews(id)
                }
            }
            val recommendations = async {
                when (type) {
                    TYPE_MOVIE -> moviesRepository.getMovieRecommendations(id)
                    else -> tvShowsRepository.getTvRecommendations(id)
                }
            }
            val videoKey = async {
                when (type) {
                    TYPE_MOVIE -> videoRepository.getMovieVideoKey(id)
                    else -> videoRepository.getTvVideoKey(id)
                }
            }
            val addedToWatchList = async {
                watchListRepository.contains(id)
            }
            val actors = async {
                when (type) {
                    TYPE_MOVIE -> actorRepository.getMovieCredits(id)
                    else -> actorRepository.getTvCredits(id)
                }
            }
            DetailPageData(
                detailData = detailData.await(),
                reviews = reviews.await(),
                recommendations = recommendations.await(),
                videoKey = videoKey.await(),
                addedToWatchList = addedToWatchList.await(),
                actors = actors.await()
            )
        }
    }

}