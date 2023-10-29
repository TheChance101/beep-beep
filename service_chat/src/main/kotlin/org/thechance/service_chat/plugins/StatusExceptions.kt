package org.thechance.service_chat.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.service_chat.endpoints.utils.configureStatusPages

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        configureStatusPages()
    }
}


