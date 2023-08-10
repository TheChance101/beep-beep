package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.domain.MissingRequestParameterException
import org.thechance.service_notification.domain.usecases.IRegisterTokenUseCase
import org.thechance.service_notification.domain.usecases.ISendNotificationToUserUseCase
import org.thechance.service_notification.domain.usecases.ISendNotificationToUsersGroupUseCase

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val sendNotificationToUsersGroup: ISendNotificationToUsersGroupUseCase by inject()
    val sendNotificationToUser: ISendNotificationToUserUseCase by inject()

    route("notifications") {

        post("/register_token/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val token = call.receiveParameters()["token"] ?: throw MissingRequestParameterException("4001")

            registerToken(userId, token)
            call.respond(HttpStatusCode.OK, "Token registered successfully")
        }

        post("/user/{userId}") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val receivedData = call.receiveParameters()
            val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
            val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")

            val result = sendNotificationToUser(userId, title, body)
            if (!result) call.respond(HttpStatusCode.InternalServerError, "Notification was not sent")
            else call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        post("/group/{usersGroup}") {
            val usersGroup = call.parameters["usersGroup"] ?: throw BadRequestException("Users group is required")
            val receivedData = call.receiveParameters()
            val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
            val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")

            val result = sendNotificationToUsersGroup(usersGroup, title, body)
            if (!result) call.respond(HttpStatusCode.InternalServerError, "Notification was not sent")
            else call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }
    }
}