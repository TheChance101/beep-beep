package org.thechance.api_gateway.plugins.utils

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun JWTAuthenticationProvider.Config.respondUnauthorized() {
    challenge { _, _ ->
        call.respond(UnauthorizedResponse())
    }
}