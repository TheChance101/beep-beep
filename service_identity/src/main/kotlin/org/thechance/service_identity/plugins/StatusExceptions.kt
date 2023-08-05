package org.thechance.service_identity.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_identity.endpoints.validation.*

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is RequestValidationException -> call.respond(
                    HttpStatusCode.BadRequest,
                    cause.reasons.map { it.toInt() })

                is InvalidHexStringLengthException -> call.respond(
                    HttpStatusCode.BadRequest,
                    listOf(cause.code)
                )

                is InvalidIDException -> call.respond(
                    HttpStatusCode.BadRequest,
                    listOf(cause.code)
                )

                is AddressNotFoundException -> call.respond(
                    HttpStatusCode.BadRequest,
                    listOf(cause.code)
                )

                is InvalidUserNameException -> call.respond(
                    HttpStatusCode.BadRequest,
                    listOf(cause.code)
                )

                is InvalidFullNameException -> call.respond(
                    HttpStatusCode.BadRequest,
                    listOf(cause.code)
                )

                is UserAlreadyExistsException -> call.respond(
                    HttpStatusCode.InternalServerError,
                    listOf(cause.code)
                )
            }
        }
    }
}