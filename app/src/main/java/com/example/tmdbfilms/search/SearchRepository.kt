package com.example.tmdbfilms.search

interface SearchRepository {

    suspend fun multiSearch(query: String): List<SearchData>

}