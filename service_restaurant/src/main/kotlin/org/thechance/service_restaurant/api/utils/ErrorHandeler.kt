package org.thechance.service_restaurant.api.utils

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

fun StatusPagesConfig.configureStatusPages() {
    exception<MultiErrorException>{ call, exception ->
        call.respond(HttpStatusCode.BadRequest, exception.errorCodes)
    }

    exception<Throwable> { call, throwable ->
        call.respond(HttpStatusCode.BadRequest, throwable.message.toString())
    }
}