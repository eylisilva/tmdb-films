package com.example.tmdbfilms.detail.review

import com.example.tmdbfilms.network.UscFilmsApiService
import java.text.SimpleDateFormat
import java.util.*

class ReviewRepository(private val apiService: UscFilmsApiService) : IReviewRepository {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
    private val outputDateFormat = SimpleDateFormat("E, MMM dd yyyy", Locale.US)

    override suspend fun getMovieReviews(movieId: Int): List<ReviewData> =
        apiService.getMovieReviews(movieId).results.map {
            transformToReviewData(it)
        }

    override suspend fun getTvReviews(tvId: Int): List<ReviewData> =
        apiService.getTvReviews(tvId).results.map {
            transformToReviewData(it)
        }

    private fun transformToReviewData(review: Review): ReviewData {
        val date = inputDateFormat.parse(review.createdAt)
        return if (date != null) {
            val formattedDate = outputDateFormat.format(date)
            ReviewData(
                "by ${review.author} on $formattedDate",
                "${review.rating / 2}/5"
            )
        } else {
            ReviewData(
                "by ${review.author}",
                "${review.rating / 2}/5"
            )
        }
    }

}