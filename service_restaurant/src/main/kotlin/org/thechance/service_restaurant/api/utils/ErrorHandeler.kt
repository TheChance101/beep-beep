package org.thechance.service_restaurant.api.utils

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import org.thechance.service_restaurant.domain.utils.InvalidParameterException
import org.thechance.service_restaurant.domain.utils.MissingParameterException
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.ResourceNotFoundException

fun StatusPagesConfig.configureStatusPages() {

    exception<MissingParameterException>{ call, exception ->
        call.respond(HttpStatusCode.BadRequest, "Missing parameter ${exception.message.toString()}")
    }
    exception<ResourceNotFoundException>{ call, exception ->
        call.respond(HttpStatusCode.NotFound, "Resource Not found ${exception.message.toString()}")
    }
    exception<MultiErrorException>{ call, exception ->
        call.respond(HttpStatusCode.NotFound, "Validation error: ${exception.message}")
    }
    exception<InvalidParameterException>{ call, exception ->
        call.respond(HttpStatusCode.BadRequest, "Validation error ${exception.message}")
    }


}