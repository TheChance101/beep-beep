package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.domain.usecases.IGetNotificationHistoryUseCase
import org.thechance.service_notification.domain.usecases.IRegisterTokenUseCase
import org.thechance.service_notification.domain.usecases.ISendNotificationContainerUseCase
import org.thechance.service_notification.endpoints.utils.extractInt
import org.thechance.service_notification.endpoints.utils.requireNotEmpty

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val getNotificationHistory: IGetNotificationHistoryUseCase by inject()
    val sendNotificationsContainer: ISendNotificationContainerUseCase by inject()

    route("notifications") {

        post("/register_token/{userId}") {
            val receivedData = call.receiveParameters()
            val token = receivedData.requireNotEmpty("token")
            val group = receivedData.requireNotEmpty("group")
            val userId = call.parameters.requireNotEmpty("userId")
            val result = registerToken(userId, token, group)
            call.respondWithResult(result, successMessage = "Token registered successfully")

        }

        post("/user/{userId}") {
            val receivedData = call.receiveParameters()
            val title = receivedData.requireNotEmpty("title")
            val body = receivedData.requireNotEmpty("body")
            val userId = call.parameters.requireNotEmpty("userId")
            val result = sendNotificationsContainer.sendNotificationToUser(userId, title, body)
            call.respondWithResult(result, successMessage = "Notification sent successfully")
        }

        post("/group/{usersGroup}") {
            val receivedData = call.receiveParameters()
            val title = receivedData.requireNotEmpty("title")
            val body = receivedData.requireNotEmpty("body")
            val usersGroup = call.parameters.requireNotEmpty("usersGroup")
            val result = sendNotificationsContainer.sendNotificationToUsersGroup(usersGroup, title, body)
            call.respondWithResult(result, successMessage = "Notification sent successfully")

        }

        get("/history") {
            val limit = call.parameters.extractInt("limit") ?: 10
            val page = call.parameters.extractInt("page") ?: 1
            val notificationsHistory = getNotificationHistory(page, limit)
            call.respond(HttpStatusCode.OK, notificationsHistory.toDto())
        }
    }

}

suspend fun ApplicationCall.respondWithResult(
    result: Boolean,
    successStatus: HttpStatusCode = HttpStatusCode.OK,
    successMessage: String = "Operation successful",
    errorStatus: HttpStatusCode = HttpStatusCode.InternalServerError,
    errorMessage: String = "Operation failed"
) {
    if (result) {
        respond(successStatus, successMessage)
    } else {
        respond(errorStatus, errorMessage)
    }
}