package org.thechance.service_restaurant.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.service_restaurant.api.utils.categoryException

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        categoryException()
    }
}


