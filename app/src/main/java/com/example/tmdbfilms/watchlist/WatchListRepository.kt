package com.example.tmdbfilms.watchlist

interface WatchListRepository {

    suspend fun updateWatchList(items: List<WatchListItem>)

    suspend fun addToWatchList(item: WatchListItem)

    suspend fun contains(key: Int): Boolean

    suspend fun getAllWatchListItems(): List<WatchListItem>

    suspend fun removeFromWatchList(key: Int)

    suspend fun move(fromPosition: Int, toPosition: Int)

}