package com.practice.model

import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap

@Serializable
data class UrlRequest(val url: String)
@Serializable
data class TinyUrlResponse(val key: String, val shortUrl: String, val longUrl: String)

//Set up the storage for the URLs. For simplicity, we'll use a map to store them in memory.
//short url as key and long url as value
val urlMap = ConcurrentHashMap<String, TinyUrlResponse>()

// Generate a short key using MD5 hash
fun generateKey(url: String): String {
    val md5 = MessageDigest.getInstance("MD5")
    val hashBytes = md5.digest(url.toByteArray())
    val hexString = StringBuilder()
    for (byte in hashBytes) {
        hexString.append(String.format("%02x", byte))
    }
    return hexString.toString().substring(0, 6) // Take first 6 characters for the key
}