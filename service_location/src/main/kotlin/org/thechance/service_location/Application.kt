package org.thechance.service_location

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_location.plugins.configureMonitoring
import org.thechance.service_location.plugins.configureRouting
import org.thechance.service_location.plugins.configureSerialization
import org.thechance.service_location.plugins.configureSockets

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    configureSerialization()
    configureMonitoring()
    configureRouting()
    configureSockets()
}
