package com.example.tmdbfilms.detail.review

import com.squareup.moshi.Json

data class Review(
    val author: String,
    val content: String,
    @Json(name = "created_at") val createdAt: String,
    val rating: Int = 0
)
