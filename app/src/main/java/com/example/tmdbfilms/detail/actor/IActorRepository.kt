package com.example.tmdbfilms.detail.actor

import com.example.tmdbfilms.detail.actor.ActorData

interface IActorRepository {

    suspend fun getMovieCredits(movieId: Int): List<ActorData>

    suspend fun getTvCredits(tvId: Int): List<ActorData>

}