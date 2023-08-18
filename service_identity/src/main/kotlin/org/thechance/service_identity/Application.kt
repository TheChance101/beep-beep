package org.thechance.service_identity

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_identity.plugins.*

fun main() {
    embeddedServer(Netty, port = 8088, host = "127.0.0.5", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting()
    configureStatusPages()
}
