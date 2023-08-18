package org.thechance.api_gateway.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.api_gateway.data.model.MultiErrorException
import org.thechance.api_gateway.data.model.MultiLocalizedMessageException

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {

    respondWithErrorCodes<MultiErrorException>(HttpStatusCode.UnprocessableEntity)

    respondWithErrorMessages<MultiLocalizedMessageException>(HttpStatusCode.UnprocessableEntity)

}

private inline fun <reified T : Throwable> StatusPagesConfig.respondWithErrorCodes(
    statusCode: HttpStatusCode,
) {
    exception<T> { call, t ->
        val reasons = t.message?.split(",")?.map { it.toInt() } ?: emptyList()
        call.respond(statusCode, reasons)
    }
}

private inline fun <reified T : Throwable> StatusPagesConfig.respondWithErrorMessages(
    statusCode: HttpStatusCode,
) {
    exception<T> { call, t ->
        val reasons = t.message?.split(",") ?: emptyList()
        call.respond(statusCode, reasons)
    }
}