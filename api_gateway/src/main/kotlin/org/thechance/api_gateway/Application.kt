package org.thechance.api_gateway

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    val tokenConfig = TokenConfiguration(
        secret = secret,
        issuer = issuer,
        audience = audience,
        accessTokenExpirationTimestamp = 356L * 24L * 60L * 60L * 1000L,
        refreshTokenExpirationTimestamp = 356L * 24L * 60L * 60L * 1000L
    )
    configureStatusPages()
    configureJWTAuthentication()
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting(tokenConfig)
}
