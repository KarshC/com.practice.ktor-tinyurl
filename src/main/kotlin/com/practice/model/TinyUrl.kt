package com.practice.model

import kotlinx.serialization.Serializable

@Serializable
data class TinyUrl(
    val key: String,
    val longUrl: String,
    val shortUrl: String,
)

