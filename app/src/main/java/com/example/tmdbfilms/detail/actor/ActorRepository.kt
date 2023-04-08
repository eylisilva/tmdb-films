package com.example.tmdbfilms.detail.actor

import com.example.tmdbfilms.network.UscFilmsApiService

class ActorRepository(private val apiService: UscFilmsApiService) : IActorRepository {

    override suspend fun getMovieCredits(movieId: Int): List<ActorData> =
        apiService.getMovieCredits(movieId).cast.take(6).map {
            ActorData(it.name, it.profilePath)
        }

    override suspend fun getTvCredits(tvId: Int): List<ActorData> =
        apiService.getTvCredits(tvId).cast.take(6).map {
            ActorData(it.name, it.profilePath)
        }

}