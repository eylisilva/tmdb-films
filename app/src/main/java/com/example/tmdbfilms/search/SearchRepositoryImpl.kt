package com.example.tmdbfilms.search

import com.example.tmdbfilms.network.UscFilmsApiService
import java.text.DecimalFormat

private const val MEDIA_TYPE_PERSON = "person"
private const val MEDIA_TYPE_TV = "tv"
private const val MEDIA_TYPE_MOVIE = "movie"
private const val MAX_NUM = 20

class SearchRepositoryImpl(private val apiService: UscFilmsApiService) : SearchRepository {

    override suspend fun multiSearch(query: String): List<SearchData> {
        val apiModel = apiService.multiSearch(query)
        val results = apiModel.results
        val data = mutableListOf<SearchData>()
        results.filterNot { it.mediaType == MEDIA_TYPE_PERSON }.take(MAX_NUM).forEach {
            val title = when (it.mediaType) {
                MEDIA_TYPE_TV -> {
                    it.name ?: ""
                }
                MEDIA_TYPE_MOVIE -> {
                    it.title ?: ""
                }
                else -> {
                    ""
                }
            }
            val rating = if (it.voteAverage == null) {
                0.0
            } else {
                it.voteAverage / 2
            }
            data.add(
                SearchData(
                    id = it.id,
                    backdropPath = it.backdropPath,
                    mediaType = it.mediaType,
                    releaseDate = it.releaseDate,
                    title = title.uppercase(),
                    rating = rating
                )
            )
        }
        return data
    }

}