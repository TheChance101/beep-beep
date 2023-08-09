package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.domain.MissingRequestParameterException
import org.thechance.service_notification.domain.usecases.INotificationManagementUseCase
import org.thechance.service_notification.endpoints.model.UserDto

fun Route.notificationRoutes() {

    val notificationManagementUseCase: INotificationManagementUseCase by inject()

    route("notification") {

        post("/user") {
            val user = call.receive<UserDto>()
            val result = notificationManagementUseCase.createUser(user.toEntity())
            call.respond(HttpStatusCode.Created, "User was created: $result")
        }

        post("/{userId}/token") {
            val userId = call.parameters["userId"] ?: throw BadRequestException("User id is required")
            val token = call.receiveParameters()["token"] ?: throw MissingRequestParameterException("4006")
            val result = notificationManagementUseCase.addTokenToUser(userId, token)
            call.respond("Token was added: $result")
        }

        post("/send") {
            val receivedData = call.receiveParameters()
            val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
            val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")
            val userId = receivedData["userId"] ?: throw MissingRequestParameterException("4004")

            notificationManagementUseCase.sendNotificationToUser(userId, title, body)
            call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

    }
}