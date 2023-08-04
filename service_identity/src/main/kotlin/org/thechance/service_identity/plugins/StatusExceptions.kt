package org.thechance.service_identity.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
//            handleException(cause, call)
        }
    }
}