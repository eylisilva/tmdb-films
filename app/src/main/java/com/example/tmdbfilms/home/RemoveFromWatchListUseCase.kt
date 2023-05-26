package com.example.tmdbfilms.home

import com.example.tmdbfilms.watchlist.WatchListRepository

class RemoveFromWatchListUseCase(private val watchListRepository: WatchListRepository) {

    suspend fun removeFromWatchList(id: Int) {
        watchListRepository.removeFromWatchList(id)
    }

}