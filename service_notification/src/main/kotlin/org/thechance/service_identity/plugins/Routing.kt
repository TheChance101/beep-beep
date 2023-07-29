package org.thechance.service_notification.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.serivce_identity.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
    }
}
