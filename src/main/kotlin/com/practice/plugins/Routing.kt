package com.practice.plugins

import com.practice.routes.tinyUrlRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        tinyUrlRoutes()
    }
}
