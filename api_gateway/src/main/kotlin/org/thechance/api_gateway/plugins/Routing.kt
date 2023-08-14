package org.thechance.api_gateway.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.api_gateway.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
    }
}
