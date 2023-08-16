package org.thechance.api_gateway.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.endpoints.userRoutes

fun Application.configureRouting(tokenConfiguration: TokenConfiguration) {
    routing {
        userRoutes(tokenConfiguration)
    }
}
