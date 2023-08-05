package org.thechance.service_identity.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_identity.domain.entity.UserAlreadyExistsException

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {
    exception<MissingRequestParameterException> { call, cause ->
        call.respond(
            HttpStatusCode.BadRequest,
            listOf(cause.message)
        )
    }

    exception<NotFoundException> { call, cause ->
        call.respond(
            HttpStatusCode.NotFound,
            listOf(cause.message)
        )
    }

    exception<UserAlreadyExistsException> { call, cause ->
        call.respond(
            HttpStatusCode.InternalServerError,
            listOf(cause.message)
        )
    }
}