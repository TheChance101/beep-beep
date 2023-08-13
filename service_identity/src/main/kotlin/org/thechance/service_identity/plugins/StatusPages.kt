package org.thechance.service_identity.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_identity.domain.entity.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {
    respondWithErrorCodes<MissingParameterException>(HttpStatusCode.BadRequest)

    respondWithErrorCodes<RequestValidationException>(HttpStatusCode.BadRequest)

    respondWithErrorCodes<UserAlreadyExistsException>(HttpStatusCode.UnprocessableEntity)

    respondWithErrorCodes<ResourceNotFoundException>(HttpStatusCode.NotFound)

    respondWithErrorCodes<InsufficientFundsException>(HttpStatusCode.UnprocessableEntity)
}

private inline fun <reified T : Throwable> StatusPagesConfig.respondWithErrorCodes(
    statusCode: HttpStatusCode,
) {
    exception<T> { call, t ->
        val reasons = t.message?.split(",")?.map { it.toInt() } ?: emptyList()
        call.respond(statusCode, reasons)
    }
}