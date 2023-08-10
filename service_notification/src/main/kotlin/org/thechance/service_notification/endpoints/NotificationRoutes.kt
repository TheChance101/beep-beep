package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.domain.MissingRequestParameterException
import org.thechance.service_notification.domain.usecases.IGetNotificationHistoryUseCase
import org.thechance.service_notification.domain.usecases.IRegisterTokenUseCase
import org.thechance.service_notification.domain.usecases.ISendNotificationContainerUseCase
import org.thechance.service_notification.endpoints.utils.extractInt

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val getNotificationHistory: IGetNotificationHistoryUseCase by inject()
    val sendNotificationsContainer: ISendNotificationContainerUseCase by inject()

    route("notifications") {

        post("/register_token/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val receivedData = call.receiveParameters()
            val token = receivedData["token"] ?: throw MissingRequestParameterException("4001")
            val group = receivedData["group"] ?: throw MissingRequestParameterException("4004")

            registerToken(userId, token, group)
            call.respond(HttpStatusCode.OK, "Token registered successfully")
        }

        post("/user/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val receivedData = call.receiveParameters()
            val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
            val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")

            val result = sendNotificationsContainer.sendNotificationToUser(userId, title, body)
            if (!result) call.respond(HttpStatusCode.InternalServerError, "Notification was not sent")
            else call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        post("/group/{usersGroup}") {
            val usersGroup = call.parameters["usersGroup"] ?: throw BadRequestException("Users group is required")
            val receivedData = call.receiveParameters()
            val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
            val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")

            val result = sendNotificationsContainer.sendNotificationToUsersGroup(usersGroup, title, body)
            if (!result) call.respond(HttpStatusCode.InternalServerError, "Notification was not sent")
            else call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        get("/history") {
            val limit = call.parameters.extractInt("limit") ?: 10
            val page = call.parameters.extractInt("page") ?: 1

            val notificationsHistory = getNotificationHistory(page, limit)
            call.respond(HttpStatusCode.OK, notificationsHistory.toDto())
        }
    }
}