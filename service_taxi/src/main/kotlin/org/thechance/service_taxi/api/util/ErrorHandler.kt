package org.thechance.service_taxi.api.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_taxi.domain.util.ALREADY_EXIST
import org.thechance.service_taxi.domain.util.AlreadyExistException
import org.thechance.service_taxi.domain.util.CantBeNullException
import org.thechance.service_taxi.domain.util.INVALID_REQUEST_PARAMETER
import org.thechance.service_taxi.domain.util.MissingParameterException
import org.thechance.service_taxi.domain.util.MultiErrorException
import org.thechance.service_taxi.domain.util.NOT_FOUND
import org.thechance.service_taxi.domain.util.REQUIRED_QUERY
import org.thechance.service_taxi.domain.util.ResourceNotFoundException
import org.thechance.service_taxi.domain.util.UNKNOWN_ERROR

fun StatusPagesConfig.configureStatusPages() {
    exception<MissingParameterException> { call, _ ->
        call.respond(HttpStatusCode.BadRequest, listOf(INVALID_REQUEST_PARAMETER))
    }

    exception<AlreadyExistException> { call, _ ->
        call.respond(HttpStatusCode.NotFound, listOf(ALREADY_EXIST))
    }

    exception<ResourceNotFoundException> { call, _ ->
        call.respond(HttpStatusCode.NotFound, listOf(NOT_FOUND))
    }

    exception<CantBeNullException> { call, _ ->
        call.respond(HttpStatusCode.BadRequest, listOf(REQUIRED_QUERY))
    }

    exception<Throwable> { call, _ ->
        call.respond(HttpStatusCode.InternalServerError, listOf(UNKNOWN_ERROR))
    }

    exception<MultiErrorException> { call, exception ->
        call.respond(HttpStatusCode.BadRequest, exception.errorCodes)
    }
}