package org.thechance.service_taxi.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.thechance.service_taxi.api.endpoints.taxiRoutes
import org.thechance.service_taxi.api.endpoints.tripRoutes

fun Application.configureRouting(
) {
    routing {
        taxiRoutes()
        tripRoutes()
    }
}
