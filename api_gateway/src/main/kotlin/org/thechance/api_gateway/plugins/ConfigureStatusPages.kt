package org.thechance.api_gateway.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.utils.respondWithError

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
        handleUnauthorizedAccess()
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


private fun StatusPagesConfig.handleUnauthorizedAccess(){
    status(HttpStatusCode.Unauthorized) { call, _ ->
        respondWithError(call, statusCode = HttpStatusCode.Unauthorized , mapOf(401 to "Access denied"))
    }
}