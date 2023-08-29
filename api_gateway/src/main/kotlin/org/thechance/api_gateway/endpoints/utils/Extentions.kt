package org.thechance.api_gateway.endpoints.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*

suspend inline fun <reified T> PipelineContext<Unit, ApplicationCall>.respondWithResult(
    statusCode: HttpStatusCode,
    result: T,
    message: String? = null
) {
    call.respond(statusCode, ServerResponse.success(result, message))
}

suspend fun respondWithError(
    call: ApplicationCall,
    statusCode: HttpStatusCode,
    errorMessage: Map<Int, String>? = null
) {
    call.respond(statusCode, ServerResponse.error(errorMessage, statusCode.value))
}

fun PipelineContext<Unit, ApplicationCall>.extractLocalizationHeader(): Pair<String?, String?> {
    val headers = call.request.headers
    val language = headers["Accept-Language"]?.trim()
    val countryCode = headers["Country-Code"]?.trim()
    return Pair(language, countryCode)
}

fun WebSocketServerSession.extractLocalizationHeaderFromWebSocket(): Pair<String?, String?> {
    val headers = call.request.headers
    val language = headers["Accept-Language"]?.trim()
    val countryCode = headers["Country-Code"]?.trim()
    return Pair(language, countryCode)
}

fun PipelineContext<Unit, ApplicationCall>.extractPermissions() : List<Int>{
    val tokenClaim = call.principal<JWTPrincipal>()
    return tokenClaim?.payload?.getClaim("permissions")?.asString()
        ?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
}

fun WebSocketServerSession.extractPermissionsFromWebSocket() : List<Int>{
    val tokenClaim = call.principal<JWTPrincipal>()
    return tokenClaim?.payload?.getClaim("permissions")?.asString()
        ?.split(",")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
}