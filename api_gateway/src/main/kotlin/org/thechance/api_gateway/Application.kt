package org.thechance.api_gateway

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.api_gateway.data.model.authenticate.TokenConfiguration
import org.thechance.api_gateway.plugins.*

fun main() {
    embeddedServer(
        Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    val secret = System.getenv("jwt_secret").toString()
    val issuer = System.getenv("jwt_issuer").toString()
    val audience = System.getenv("jwt_audience").toString()

    val tokenConfig = TokenConfiguration(
        secret = secret,
        issuer = issuer,
        audience = audience,
        accessTokenExpirationTimestamp = 356L * 24L * 60L * 60L * 1000L,
        refreshTokenExpirationTimestamp = 356L * 24L * 60L * 60L * 1000L
    )
    configureStatusPages()
    configureSockets()
    configureJWTAuthentication()
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting(tokenConfig)
}
