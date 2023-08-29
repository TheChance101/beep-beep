package org.thechance.api_gateway.endpoints.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.pipeline.*
import org.thechance.api_gateway.util.Role

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

fun PipelineContext<Unit, ApplicationCall>.extractPermission(): Int {
    val tokenClaim = call.principal<JWTPrincipal>()
    return tokenClaim?.payload?.getClaim("permission")?.asString()?.toInt() ?: -1
}

fun Route.authenticateWithRole(role: Int, block: Route.() -> Unit) {
    authenticate("auth-jwt", "refresh-jwt") {
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

fun main() {
    val token = 21
    val roles = listOf(
        Role.END_USER, // true
        Role.DASHBOARD_ADMIN, // false
        Role.RESTAURANT_OWNER, // true
        Role.TAXI_DRIVER, // false
        Role.SUPPORT, // true
        Role.DELIVERY // false
    )
    for (role in roles) {
        println(hasPermission(token, role))
    }
}