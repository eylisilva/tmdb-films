package com.example.tmdbfilms.detail.video

interface IVideoRepository {

    suspend fun getMovieVideoKey(movieId: Int): String?

    suspend fun getTvVideoKey(tvId: Int): String?

}