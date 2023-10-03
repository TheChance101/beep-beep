package org.thechance.service_chat

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_chat.plugins.*
import org.thechance.service_chat.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8057, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDependencyInjection()
    configureSockets()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}
