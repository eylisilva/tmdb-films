package com.example.tmdbfilms.network

import android.util.Log
import com.example.tmdbfilms.home.movie.MovieResultsApiModel
import com.example.tmdbfilms.home.tvshows.TvShowResultsApiModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val TAG = "UscFilmsApiService"

private const val BASE_URL = "https://api.themoviedb.org/3/"

private const val API_KEY = "93c502ad3ecf767be3daa1f584f33453"

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

    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResultsApiModel

    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(): TvShowResultsApiModel

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(): TvShowResultsApiModel

}

object UscFilmsApi {
    val retrofitService: UscFilmsApiService by lazy {
        retrofit.create(UscFilmsApiService::class.java)
    }
}



