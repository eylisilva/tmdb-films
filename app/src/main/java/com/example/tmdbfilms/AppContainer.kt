package com.example.tmdbfilms

import android.content.Context
import com.example.tmdbfilms.detail.DetailRepository
import com.example.tmdbfilms.detail.IDetailRepository
import com.example.tmdbfilms.detail.review.IReviewRepository
import com.example.tmdbfilms.detail.review.ReviewRepository
import com.example.tmdbfilms.detail.video.IVideoRepository
import com.example.tmdbfilms.detail.video.VideoRepository
import com.example.tmdbfilms.home.movie.MoviesRepository
import com.example.tmdbfilms.home.movie.MoviesRepositoryImpl
import com.example.tmdbfilms.home.tvshows.TvShowsRepository
import com.example.tmdbfilms.home.tvshows.TvShowsRepositoryImpl
import com.example.tmdbfilms.network.UscFilmsApi
import com.example.tmdbfilms.search.SearchRepository
import com.example.tmdbfilms.search.SearchRepositoryImpl
import com.example.tmdbfilms.watchlist.WatchListRepository
import com.example.tmdbfilms.watchlist.WatchListRepositoryImpl

interface AppContainer {
    val moviesRepository: MoviesRepository
    val tvShowsRepository: TvShowsRepository
    val searchRepository: SearchRepository
    val watchListRepository: WatchListRepository
    val videoRepository: IVideoRepository
    val detailRepository: IDetailRepository
    val reviewRepository: IReviewRepository
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

    override val watchListRepository: WatchListRepository
        get() = WatchListRepositoryImpl(applicationContext)

    override val videoRepository: IVideoRepository
        get() = VideoRepository(UscFilmsApi.retrofitService)

    override val detailRepository: IDetailRepository
        get() = DetailRepository(UscFilmsApi.retrofitService)

    override val reviewRepository: IReviewRepository
        get() = ReviewRepository(UscFilmsApi.retrofitService)


}