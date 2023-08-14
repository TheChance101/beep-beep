package org.thechance.service_notification.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_notification.domain.entity.InternalServerErrorException
import org.thechance.service_notification.domain.entity.NotFoundException
import org.thechance.service_notification.domain.entity.ResourceAlreadyExistsException

fun Application.configureStatusPage() {
    install(StatusPages) {
        handleExceptions()
    }
}

fun StatusPagesConfig.handleExceptions() {

    exception<MissingRequestParameterException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, listOf(cause.message?.toInt()))
    }

    exception<NotFoundException> { call, cause ->
        call.respond(HttpStatusCode.NotFound, listOf(cause.message?.toInt()))
    }

    exception<InternalServerErrorException> { call, cause ->
        call.respond(HttpStatusCode.InternalServerError, listOf(cause.message?.toInt()))
    }

    exception<ResourceAlreadyExistsException> { call, cause ->
        call.respond(HttpStatusCode.Conflict, listOf(cause.message?.toInt()))
    }

}
