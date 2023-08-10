package org.thechance.service_notification.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.service_notification.endpoints.notificationRoutes

fun Application.configureRouting(
) {
    routing {
        notificationRoutes()
    }
}
