package org.thechance.service_notification.plugins

import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.thechance.service_notification.endpoints.notificationHistoryRoutes
import org.thechance.service_notification.endpoints.notificationManagementRoutes

fun Application.configureRouting(
) {
    routing {
        notificationHistoryRoutes()
        notificationManagementRoutes()
    }
}
