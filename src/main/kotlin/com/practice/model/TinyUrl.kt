package com.practice.model

import kotlinx.serialization.Serializable

@Serializable
data class TinyUrl(
    val key: String,
    val longUrl: String,
    val shortUrl: String,
)

//Using in-memory storage (mutableList of TinyUrl here) for storing data
val tinyUrlStorage = mutableListOf<TinyUrl>()