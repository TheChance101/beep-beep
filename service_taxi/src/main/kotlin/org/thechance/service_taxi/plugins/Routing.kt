package org.thechance.service_taxi.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_taxi.api.endpoints.taxiRoutes

fun Application.configureRouting(
) {
    routing {
        taxiRoutes()
    }
}
