package org.thechance.api_gateway

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.api_gateway.plugins.configureDependencyInjection
import org.thechance.api_gateway.plugins.configureMonitoring
import org.thechance.api_gateway.plugins.configureRouting
import org.thechance.api_gateway.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
