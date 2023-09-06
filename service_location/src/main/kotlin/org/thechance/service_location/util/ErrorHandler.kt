package org.thechance.service_location.util

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.configureStatusPages() {
    exception<MultiErrorException> { call, exception ->
        call.respond(HttpStatusCode.NotFound, exception.errorCodes)
    }

    exception<Throwable> { call, throwable ->
        call.respond(HttpStatusCode.BadRequest, throwable.message.toString())
    }
}