package org.thechance.api_gateway.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.utils.respondWithError

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {
    exception<LocalizedMessageException> { call, t ->
        respondWithError(call, HttpStatusCode.BadRequest, t.errorMessages)
    }
    exception<SecurityException>{ call, t ->
        respondWithError(call, HttpStatusCode.Unauthorized, t.message?.let { mapOf(401 to it) })
    }
}