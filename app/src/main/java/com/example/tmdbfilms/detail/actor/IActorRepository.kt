package com.example.tmdbfilms.detail.actor


interface IActorRepository {

    suspend fun getMovieCredits(movieId: Int): List<ActorData>

    suspend fun getTvCredits(tvId: Int): List<ActorData>

}