package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Parameters.extractString(key: String): String? {
    return this[key]?.trim()?.takeIf { it.isNotEmpty() }
}

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}

suspend fun ApplicationCall.badRequest(errorMessage: String) {
    this.respond(HttpStatusCode.BadRequest, errorMessage)
}