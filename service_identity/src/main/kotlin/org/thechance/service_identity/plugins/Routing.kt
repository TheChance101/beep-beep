package org.thechance.service_identity.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.thechance.service_identity.api.endpoints.testRoutes
import org.thechance.service_identity.api.endpoints.walletRoute

fun Application.configureRouting(
) {
    routing {
        walletRoute()
        testRoutes()
    }
}
