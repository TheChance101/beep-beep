package org.thechance.api_gateway.endpoints.utils

import io.ktor.server.application.*
import io.ktor.util.pipeline.*

fun PipelineContext<Unit, ApplicationCall>.extractLocalizationHeader(): Pair<String?, String?> {
    val headers = call.request.headers
    val language = headers["Accept-Language"]?.trim()
    val countryCode = headers["Country-Code"]?.trim()
    return Pair(language, countryCode)
}