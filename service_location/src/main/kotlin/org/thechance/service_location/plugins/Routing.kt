package org.thechance.service_location.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.service_location.endpoints.locationRoutes

fun Application.configureRouting(
) {
    routing {
        locationRoutes()
    }
}
