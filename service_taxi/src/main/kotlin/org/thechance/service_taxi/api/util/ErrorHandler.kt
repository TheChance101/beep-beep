package org.thechance.service_taxi.api.util

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_taxi.domain.exceptions.ALREADY_EXIST
import org.thechance.service_taxi.domain.exceptions.AlreadyExistException
import org.thechance.service_taxi.domain.exceptions.CantBeNullException
import org.thechance.service_taxi.domain.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_taxi.domain.exceptions.MissingParameterException
import org.thechance.service_taxi.domain.exceptions.MultiErrorException
import org.thechance.service_taxi.domain.exceptions.NOT_FOUND
import org.thechance.service_taxi.domain.exceptions.REQUIRED_QUERY
import org.thechance.service_taxi.domain.exceptions.ResourceNotFoundException
import org.thechance.service_taxi.domain.exceptions.UNKNOWN_ERROR

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

    exception<Throwable> { call, th ->
        println("\n\n***** ERROR(${th.message}) *****\n\n")
        call.respond(HttpStatusCode.InternalServerError, listOf(UNKNOWN_ERROR))
    }

    exception<MultiErrorException> { call, exception ->
        call.respond(HttpStatusCode.BadRequest, exception.errorCodes)
    }
}