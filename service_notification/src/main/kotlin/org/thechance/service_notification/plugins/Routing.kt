package org.thechance.service_notification.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_notification.endpoints.notificationRoutes
import org.thechance.service_notification.endpoints.testRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.thechance.service_notification.endpoints.userRoutes

fun Application.configureRouting(
) {
    routing {
        notificationRoutes()
        userRoutes()
    }
}
