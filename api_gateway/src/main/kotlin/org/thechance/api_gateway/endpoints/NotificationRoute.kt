package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.notification.NotificationDto
import org.thechance.api_gateway.data.service.NotificationService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Claim
import org.thechance.api_gateway.util.Role

fun Route.notificationRoute() {

    val notificationService: NotificationService by inject()

    route("/tokens") {
        get("/user/{id}") {
            val language = extractLocalizationHeader()
            val id = call.parameters["id"]?.trim().toString()
            val result = notificationService.getUserToken(id, language)
            respondWithResult(HttpStatusCode.OK, result)
        }
        get("/users") {
            val language = extractLocalizationHeader()
            val ids: List<String> = call.receive<List<String>>()
            val result = notificationService.getAllUsersTokens(ids, language)
            respondWithResult(HttpStatusCode.OK, result)
        }

        authenticateWithRole(Role.END_USER) {
            post("/save-token") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val userToken = call.parameters["token"].orEmpty()
                val language = extractLocalizationHeader()
                val result = notificationService.saveToken(userId, userToken, language)
                respondWithResult(HttpStatusCode.Created, result)
            }
        }
    }

    route("notifications") {
        post("send/user/{userId}") {
            val language = extractLocalizationHeader()
            val userId = call.parameters["userId"]?.trim().orEmpty()
            val receivedData = call.receive<NotificationDto>()
            val result = notificationService.sendNotificationToUser(userId, receivedData, language)
            respondWithResult(HttpStatusCode.OK, result)
        }
        authenticateWithRole(Role.END_USER) {
            get("/history/{userId}") {
                val tokenClaim = call.principal<JWTPrincipal>()
                val userId = tokenClaim?.get(Claim.USER_ID).toString()
                val language = extractLocalizationHeader()
                val page = call.parameters["page"].orEmpty()
                val limit = call.parameters["limit"].orEmpty()
                val result = notificationService.getNotificationHistoryForUser(userId, page, limit, language)
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}