package com.example.tmdbfilms.home

import com.example.tmdbfilms.watchlist.WatchListRepository

class IsContainedInWatchListUseCase(private val watchListRepository: WatchListRepository) {

    suspend fun isContainedInWatchList(key: Int): Boolean = watchListRepository.contains(key)

}