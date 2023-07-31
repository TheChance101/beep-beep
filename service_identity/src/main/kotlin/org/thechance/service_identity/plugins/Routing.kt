package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.service_identity.api.endpoints.addressRoutes

fun Application.configureRouting() {
    routing {
        addressRoutes()
    }
}
