package org.thechance.service_taxi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_taxi.plugins.configureDependencyInjection
import org.thechance.service_taxi.plugins.configureMonitoring
import org.thechance.service_taxi.plugins.configureRouting
import org.thechance.service_taxi.plugins.configureSerialization

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
