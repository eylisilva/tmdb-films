package com.example.tmdbfilms.home

import com.example.tmdbfilms.watchlist.WatchListItem
import com.example.tmdbfilms.watchlist.WatchListRepository

class AddToWatchListUseCase(private val watchListRepository: WatchListRepository) {

    suspend fun addToWatchList(item: WatchListItem) {
        watchListRepository.addToWatchList(item)
    }

}