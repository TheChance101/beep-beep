package org.thechance.service_notification


import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.thechance.service_notification.plugins.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureFirebaseApp()
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()
    configureRouting()
    configureStatusPage()
}
