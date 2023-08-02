package org.thechance.service_restaurant.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.service_restaurant.api.utils.errorsException

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        errorsException()
    }
}


