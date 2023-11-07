package org.thechance.service_identity.endpoints.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import org.thechance.service_identity.domain.util.ApplicationId

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}

fun String?.toIntListOrNull(): List<Int>? {
    return this?.split(",")?.mapNotNull { it.toIntOrNull() }
}

fun PipelineContext<Unit, ApplicationCall>.extractApplicationIdHeader(): String {
    val headers = call.request.headers
    val applicationId = headers["Application-Id"]?.trim()
    return if (applicationId.isNullOrBlank()) {
        System.getenv(ApplicationId.END_USER).toString()
    } else {
        applicationId
    }
}
