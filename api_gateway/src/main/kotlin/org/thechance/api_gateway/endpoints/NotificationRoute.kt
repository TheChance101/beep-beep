package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.NotificationDto
import org.thechance.api_gateway.data.service.NotificationService
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult

fun Route.notificationRoute() {


    val notificationService: NotificationService by inject()


    route("notifications") {
        post("send/user/{userId}") {
            val language = extractLocalizationHeader()
            val userId = call.parameters["userId"]?.trim().orEmpty()
            val receivedData = call.receive<NotificationDto>()
            val result = notificationService.sendNotificationToUser(userId, receivedData, language)
            respondWithResult(HttpStatusCode.OK, result)
        }
    }

}
