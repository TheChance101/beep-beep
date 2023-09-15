package org.thechance.service_taxi

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.thechance.service_taxi.plugins.configureDependencyInjection
import org.thechance.service_taxi.plugins.configureMonitoring
import org.thechance.service_taxi.plugins.configureRouting
import org.thechance.service_taxi.plugins.configureSerialization
import org.thechance.service_taxi.plugins.configureStatusExceptions

fun main() {
    embeddedServer(Netty, port = 8021, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureStatusExceptions()
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
