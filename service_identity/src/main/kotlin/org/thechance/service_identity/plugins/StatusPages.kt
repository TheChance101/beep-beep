package org.thechance.service_identity.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_identity.domain.entity.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {
    exception<MissingParameterException> { call, cause ->
        call.respond(
            HttpStatusCode.BadRequest,
            listOf(cause.message?.toInt())
        )
    }

    exception<RequestValidationException> { call, cause ->
        val reasons = cause.message?.split(",")?.map { it.toInt() } ?: emptyList()
        call.respond(HttpStatusCode.BadRequest, reasons)
    }

    exception<NotFoundException> { call, cause ->
        call.respond(
            HttpStatusCode.NotFound,
            listOf(cause.message?.toInt())
        )
    }

    exception<UserAlreadyExistsException> { call, cause ->
        call.respond(
            HttpStatusCode.InternalServerError,
            listOf(cause.message?.toInt())
        )
    }

    exception<ResourceNotFoundException> { call, cause ->
        call.respond(HttpStatusCode.NotFound, listOf(cause.message?.toInt()))
    }

    exception<InsufficientFundsException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, listOf(cause.message?.toInt()))
    }
}