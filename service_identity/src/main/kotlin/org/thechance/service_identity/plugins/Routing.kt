package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.service_identity.endpoints.*

fun Application.configureRouting() {
    routing {
        walletRoute()
        userRoutes()
        addressRoutes()
        permissionRoutes()
        userManagementRoutes()
    }
}
