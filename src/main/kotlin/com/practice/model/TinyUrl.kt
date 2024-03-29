package com.practice.model

import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.security.MessageDigest

@Serializable
data class TinyUrl(
    val key: String?,
    val longUrl: String,
    val shortUrl: String?,
)

//Using in-memory storage (mutableMap of TinyUrl here) for storing data
val tinyUrlStorage = mutableMapOf<String, TinyUrl>()

//Method to encode the longUrl to id
fun String.encodeToId(length: Int = 6): String {
    val hashBytes = MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))
    val hashString = String.format("%032x", BigInteger(1, hashBytes))
    return hashString.take(length)
}

//identifier collision checking
fun getIdentifier(url: String, length: Int = 6): String{
    val id = url.encodeToId()
    val retrievedResponse = tinyUrlStorage[id]
    if(retrievedResponse?.longUrl != url){
        return getIdentifier(url, length + 1)
    }
    return id
}

//Method to decode the shortUrl to longUrl using id
fun String.decodeToLongUrl(): String{
    val tinyUrlData = tinyUrlStorage[this]!!
    return tinyUrlData.longUrl
}