package org.thechance.service_chat.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_chat.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
    }
}
