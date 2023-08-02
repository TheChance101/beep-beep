package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.authErrorsException() {

    exception<IdNotFoundException> { call, _ ->
        call.badRequest("IdNotFoundException.")
    }

    exception<GeneralException> { call, _ ->
        call.badRequest("GeneralException.")
    }
}


suspend fun ApplicationCall.badRequest(errorMessage: String) {
    this.respond(HttpStatusCode.BadRequest, errorMessage)
}