package org.thechance.service_taxi.api.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_taxi.domain.util.CantBeNullException
import org.thechance.service_taxi.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_taxi.domain.util.MissingParameterException
import org.thechance.service_taxi.domain.util.MultiErrorException
import org.thechance.service_taxi.domain.util.NOT_FOUND
import org.thechance.service_taxi.domain.util.REQUIRED_QUERY
import org.thechance.service_taxi.domain.util.ResourceNotFoundException

fun StatusPagesConfig.configureStatusPages() {
    exception<MissingParameterException> { call, _ ->
        call.respond(HttpStatusCode.BadRequest, INVALID_REQUEST_PARAMETER)
    }

    exception<ResourceNotFoundException> { call, _ ->
        call.respond(HttpStatusCode.NotFound, NOT_FOUND)
    }
    exception<CantBeNullException> { call, _ ->
        call.respond(HttpStatusCode.BadRequest, REQUIRED_QUERY)
    }
    exception<MultiErrorException> { call, exception ->
        call.respond(HttpStatusCode.BadRequest, exception.errorCodes)
    }
    exception<Throwable> { call, throwable ->
        call.respond(HttpStatusCode.InternalServerError, throwable.message ?: "Unknown Error")
    }
}