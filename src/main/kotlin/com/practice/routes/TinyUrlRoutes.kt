package com.practice.routes

import com.practice.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tinyUrlRoutes() {
    route("/tinyurlgenerator") {
        post("/") {
            val request = call.receive<UrlRequest>()

            if (request.url.isNullOrEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Missing field: url")
                return@post
            }

            val existingShortenedUrl = urlMap.values.find { it.longUrl == request.url }
            if (existingShortenedUrl != null) {
                call.respond(existingShortenedUrl)
                return@post
            }

            val key = generateKey(request.url)
            val shortenedUrl = TinyUrlResponse(key, request.url, "http://localhost/$key")
            urlMap[key] = shortenedUrl

            call.respond(shortenedUrl)
        }
        delete("/{key}") {
            val key = call.parameters["key"]
            val removedUrl = urlMap.remove(key)

            if (removedUrl != null) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get("/{key}") {
            val key = call.parameters["key"]
            val shortenedUrl = urlMap[key]

            if (shortenedUrl != null) {
                call.response.headers.append(HttpHeaders.Location, shortenedUrl.longUrl)
                call.respond(HttpStatusCode.Found, "")
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}