package org.thechance.service_identity.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_identity.api.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
    }
}
