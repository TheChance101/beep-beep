package org.thechance.service_restaurant.api.utils

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

fun StatusPagesConfig.configureStatusPages() {
    exception<MultiErrorException>{ call, exception ->
        call.respond(HttpStatusCode.NotFound, exception.errorCodes)
    }
}