package org.thechance.service_notification.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_notification.domain.NotFoundException

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

}
