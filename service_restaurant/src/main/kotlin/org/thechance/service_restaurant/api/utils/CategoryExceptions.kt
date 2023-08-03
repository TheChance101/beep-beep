package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.thechance.service_restaurant.utils.DeleteCategoryException

fun StatusPagesConfig.categoryException() {

    exception<DeleteCategoryException> { call, exception ->
        call.respond(HttpStatusCode.BadRequest,exception.message.toString() )
    }

}