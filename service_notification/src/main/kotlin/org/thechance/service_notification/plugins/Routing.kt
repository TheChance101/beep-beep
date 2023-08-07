package org.thechance.service_notification.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_notification.endpoints.notificationRoutes
import org.thechance.service_notification.endpoints.testRoutes

fun Application.configureRouting(
) {
    routing {
        testRoutes()
        notificationRoutes()
    }
}
