package com.example.tmdbfilms.network

import com.example.tmdbfilms.home.movie.MovieResultsApiModel
import com.example.tmdbfilms.home.tvshows.TvShowResultsApiModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3"

private const val API_KEY = "93c502ad3ecf767be3daa1f584f33453"

private val client = OkHttpClient().apply {
    interceptors().add(Interceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", "en-US")
            .addQueryParameter("page", "1")
            .build()
        chain.proceed(request.newBuilder().url(url).build())
    })
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface UscFilmsApiService {

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieResultsApiModel

    @GET("/tv/trending")
    suspend fun getTrendingTvShows(): TvShowResultsApiModel

}

object UscFilmsApi {
    val retrofitService: UscFilmsApiService by lazy {
        retrofit.create(UscFilmsApiService::class.java)
    }
}



