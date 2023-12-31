package org.thechance.service_location.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.service_location.util.configureStatusPages

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        configureStatusPages()
    }
}

