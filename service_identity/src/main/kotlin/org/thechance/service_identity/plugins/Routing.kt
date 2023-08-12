package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.service_identity.endpoints.addressRoutes
import org.thechance.service_identity.endpoints.permissionRoutes
import org.thechance.service_identity.endpoints.userManagementRoutes
import org.thechance.service_identity.endpoints.userRoutes

fun Application.configureRouting() {
    routing {
        userRoutes()
        addressRoutes()
        permissionRoutes()
        userManagementRoutes()
    }
}
