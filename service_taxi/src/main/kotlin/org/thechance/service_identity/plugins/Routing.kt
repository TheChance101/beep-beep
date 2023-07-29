package org.thechance.service_taxi.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_taxi.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
    }
}
