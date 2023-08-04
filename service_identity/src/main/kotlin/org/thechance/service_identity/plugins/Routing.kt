package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import org.thechance.service_identity.api.endpoints.userRoutes
import org.thechance.service_identity.api.endpoints.walletRoute
import io.ktor.server.routing.*
import org.thechance.service_identity.endpoints.addressRoutes
import org.thechance.service_identity.api.endpoints.permissionRoutes

fun Application.configureRouting() {
    routing {
        walletRoute()
        userRoutes()
        addressRoutes()
        permissionRoutes()
    }
}
