package org.thechance.api_gateway.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import org.thechance.api_gateway.data.model.MultiLocalizedMessageException
import org.thechance.api_gateway.endpoints.utils.respondWithError

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {

    respondWithErrorMessages<MultiLocalizedMessageException>(HttpStatusCode.UnprocessableEntity)

}

private inline fun <reified T : Throwable> StatusPagesConfig.respondWithErrorMessages(
    statusCode: HttpStatusCode,
) {
    exception<T> { call, t ->
        val reasons = t.message?.split(",") ?: emptyList()

        respondWithError(call, statusCode, reasons)
    }
}

