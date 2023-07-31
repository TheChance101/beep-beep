package org.thechance.service_identity

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_identity.plugins.configureDependencyInjection
import org.thechance.service_identity.plugins.configureMonitoring
import org.thechance.service_identity.plugins.configureRouting
import org.thechance.service_identity.plugins.configureSerialization

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
