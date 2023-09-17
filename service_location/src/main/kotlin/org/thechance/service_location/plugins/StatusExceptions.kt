package org.thechance.service_location.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_location.util.InvalidLocationException
import org.thechance.service_location.util.MissingParameterException

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {

    respondWithErrorCodes<MissingParameterException>(HttpStatusCode.BadRequest)

    respondWithErrorCodes<InvalidLocationException>(HttpStatusCode.BadRequest)
}

private inline fun <reified T : Throwable> StatusPagesConfig.respondWithErrorCodes(
    statusCode: HttpStatusCode,
) {
    exception<T> { call, t ->
        val reasons = t.message?.split(",")?.map { it.toInt() } ?: emptyList()
        call.respond(statusCode, reasons)
    }
}

