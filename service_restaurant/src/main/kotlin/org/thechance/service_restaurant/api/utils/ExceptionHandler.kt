package org.thechance.service_restaurant.api.utils

import io.ktor.server.plugins.statuspages.*

fun StatusPagesConfig.errorsException() {

    exception<IdNotFoundException> { call, _ ->
        call.badRequest("IdNotFoundException.")
    }

    exception<GeneralException> { call, _ ->
        call.badRequest("GeneralException.")
    }
}