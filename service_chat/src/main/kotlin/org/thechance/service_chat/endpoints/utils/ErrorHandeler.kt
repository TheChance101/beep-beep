package org.thechance.service_chat.endpoints.utils

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_chat.domain.utils.MultiErrorException

fun StatusPagesConfig.configureStatusPages() {
    exception<MultiErrorException>{ call, exception ->
        call.respond(HttpStatusCode.BadRequest, exception.errorCodes)
    }

    exception<Throwable> { call, throwable ->
        call.respond(HttpStatusCode.BadRequest, throwable.message.toString())
    }
}