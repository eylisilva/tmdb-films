package com.example.tmdbfilms.detail.video

import com.example.tmdbfilms.network.UscFilmsApiService

class VideoRepository(private val apiService: UscFilmsApiService): IVideoRepository {

    override suspend fun getMovieVideoKey(movieId: Int): String? {
        val apiModel = apiService.getMovieVideos(movieId)
        return if (apiModel.results.isNotEmpty()) {
            apiModel.results[0].key
        } else {
            null
        }
    }

    override suspend fun getTvVideoKey(tvId: Int): String? {
        val apiModel = apiService.getTvVideos(tvId)
        return if (apiModel.results.isNotEmpty()) {
            apiModel.results[0].key
        } else {
            null
        }
    }

}