package org.thechance.api_gateway.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.api_gateway.data.model.MultiLocalizedMessageException

fun Application.configureStatusPages() {
    install(StatusPages) {
        handleStatusPagesExceptions()
    }
}

private fun StatusPagesConfig.handleStatusPagesExceptions() {

    exception<MultiLocalizedMessageException> { call, t ->
        call.respond(HttpStatusCode.BadRequest, t.errors)
    }

}