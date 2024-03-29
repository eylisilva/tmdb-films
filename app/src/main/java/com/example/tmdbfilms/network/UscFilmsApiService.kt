package com.example.tmdbfilms.network

import android.util.Log
import com.example.tmdbfilms.BuildConfig
import com.example.tmdbfilms.detail.*
import com.example.tmdbfilms.detail.actor.CreditsApiModel
import com.example.tmdbfilms.detail.review.ReviewsApiModel
import com.example.tmdbfilms.detail.video.VideosApiModel
import com.example.tmdbfilms.home.movie.MovieResultsApiModel
import com.example.tmdbfilms.home.tvshows.TvShowResultsApiModel
import com.example.tmdbfilms.search.SearchResultsApiModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TAG = "UscFilmsApiService"

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val API_KEY = BuildConfig.API_KEY

object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

private val moshi = Moshi.Builder()
    .add(NullToEmptyStringAdapter)
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url
    val url = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .addQueryParameter("language", "en-US")
        .addQueryParameter("page", "1")
        .build()
    val requestBuilder = original.newBuilder().url(url)
    val request = requestBuilder.build()
    val response = chain.proceed(request)
    Log.i(TAG, "response interceptor: $response")
    response
}).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface UscFilmsApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResultsApiModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieResultsApiModel

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResultsApiModel

    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(): TvShowResultsApiModel

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(): TvShowResultsApiModel

    @GET("tv/popular")
    suspend fun getPopularTvShows(): TvShowResultsApiModel

    @GET("search/multi")
    suspend fun multiSearch(@Query("query") query: String): SearchResultsApiModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailApiModel

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(@Path("tv_id") tvId: Int): TvDetailApiModel

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): CreditsApiModel

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCredits(@Path("tv_id") tvId: Int): CreditsApiModel

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId: Int): ReviewsApiModel

    @GET("tv/{tv_id}/reviews")
    suspend fun getTvReviews(@Path("tv_id") tvId: Int): ReviewsApiModel

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int): MovieResultsApiModel

    @GET("tv/{tv_id}/recommendations")
    suspend fun getTvRecommendations(@Path("tv_id") tvId: Int): TvShowResultsApiModel

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: Int): VideosApiModel

    @GET("tv/{tv_id}/videos")
    suspend fun getTvVideos(@Path("tv_id") tvId: Int): VideosApiModel

}

object UscFilmsApi {
    val retrofitService: UscFilmsApiService by lazy {
        retrofit.create(UscFilmsApiService::class.java)
    }
}



