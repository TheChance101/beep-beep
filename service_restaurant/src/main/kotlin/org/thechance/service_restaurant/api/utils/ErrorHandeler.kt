package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_restaurant.utils.*

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