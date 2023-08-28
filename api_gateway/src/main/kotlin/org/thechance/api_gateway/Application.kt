package org.thechance.api_gateway

import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.plugins.*

//fun main(args: Array<String>): Unit = EngineMain.main(args)

fun main() {
    embeddedServer(Netty, port = 8089, host = "0.0.0.0", module = Application::module).start(wait = true)
}
fun Application.module() {

//    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()
    val secret = ApplicationConfig("jwt.secret").toString()
    val issuer = ApplicationConfig("jwt.issuer").toString()
    val audience = ApplicationConfig("jwt.audience").toString()
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
