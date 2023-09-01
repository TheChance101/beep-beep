package org.thechance.api_gateway.endpoints.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import org.thechance.api_gateway.data.localizedMessages.Country
import org.thechance.api_gateway.data.localizedMessages.Language
import org.thechance.api_gateway.util.Claim.PERMISSION
import org.thechance.api_gateway.util.Role

suspend inline fun <reified T> PipelineContext<Unit, ApplicationCall>.respondWithResult(
    statusCode: HttpStatusCode, result: T, message: String? = null
) {
    call.respond(statusCode, ServerResponse.success(result, message))
}

suspend fun respondWithError(
    call: ApplicationCall, statusCode: HttpStatusCode, errorMessage: Map<Int, String>? = null
) {
    call.respond(statusCode, ServerResponse.error(errorMessage, statusCode.value))
}

fun PipelineContext<Unit, ApplicationCall>.extractLocalizationHeader(): Pair<String, String> {
    val headers = call.request.headers
    val language = headers["Accept-Language"]?.trim() ?: Language.ENGLISH.code
    val countryCode = headers["Country-Code"]?.trim() ?: Country.EGYPT.code
    return Pair(language, countryCode)
}

fun WebSocketServerSession.extractLocalizationHeaderFromWebSocket(): Pair<String, String> {
    val headers = call.request.headers
    val language = headers["Accept-Language"]?.trim() ?: Language.ENGLISH.code
    val countryCode = headers["Country-Code"]?.trim() ?: Country.EGYPT.code
    return Pair(language, countryCode)
}

private fun PipelineContext<Unit, ApplicationCall>.extractPermission(): Int {
    val principal = call.principal<JWTPrincipal>()
    return principal?.getClaim(PERMISSION, Int::class) ?: -1
}

fun Route.authenticateWithRole(role: Int, block: Route.() -> Unit) {
    authenticate("auth-jwt") {
        intercept(ApplicationCallPipeline.Call) {
            val permission = extractPermission()
            if (!hasPermission(permission, role)) {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
        block()
    }
}

private fun hasPermission(permission: Int, role: Int): Boolean {
    return (permission and role) == role
}
