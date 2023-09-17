package org.thechance.service_location

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_location.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureStatusExceptions()
    configureSerialization()
    configureMonitoring()
    configureSockets()
    configureRouting()
    configureDependencyInjection()
}
