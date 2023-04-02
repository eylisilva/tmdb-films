package com.example.tmdbfilms.detail.review

interface IReviewRepository {

    suspend fun getMovieReviews(movieId: Int): List<ReviewData>

    suspend fun getTvReviews(tvId: Int): List<ReviewData>

}