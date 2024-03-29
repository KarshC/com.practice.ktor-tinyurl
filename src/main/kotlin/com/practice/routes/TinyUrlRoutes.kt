package com.practice.routes

import com.practice.model.TinyUrl
import com.practice.model.decodeToLongUrl
import com.practice.model.getIdentifier
import com.practice.model.tinyUrlStorage
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getTinyUrl() {

    route("/tinyUrl") {
        get("/{id}") {
            val id = call.parameters["id"]
            val retrievedUrl = id?.let {
                it.decodeToLongUrl()
            }

            if (id.isNullOrBlank() || retrievedUrl == null) {
                return@get call.respondRedirect("https://www.google.com")
            }

            call.respondRedirect(retrievedUrl)
        }

        post {
            val request = call.receive<TinyUrl>()
            val id = getIdentifier(request.longUrl)
            val retrievedResponse = tinyUrlStorage[id]

            if (retrievedResponse != null) {
                return@post call.respond(retrievedResponse)
            }

            val response = TinyUrl(
                key = id,
                longUrl = request.longUrl,
                shortUrl = "http://localhost:8080/$id"
            )

            tinyUrlStorage[id] = response

            call.respond(response)
        }
    }
}