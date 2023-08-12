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
import org.thechance.service_notification.domain.usecases.ISendNotificationToTopicContainer
import org.thechance.service_notification.endpoints.utils.extractInt
import org.thechance.service_notification.endpoints.utils.requireNotEmpty

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val getNotificationHistory: IGetNotificationHistoryUseCase by inject()
    val sendNotificationsContainer: ISendNotificationContainerUseCase by inject()
    val sendNotificationToTopicContainer: ISendNotificationToTopicContainer by inject()

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

        get("/history") {
            val limit = call.parameters.extractInt("limit") ?: 10
            val page = call.parameters.extractInt("page") ?: 1
            val notificationsHistory = getNotificationHistory(page, limit)
            call.respond(HttpStatusCode.OK, notificationsHistory.toDto())
        }

        post("/topic") {
            val topicName = call.parameters.requireNotEmpty("topic")
            sendNotificationToTopicContainer.createTopic(topicName)
            call.respond(HttpStatusCode.Created, "Topic Created Successfully")
        }

        get("/topic") {
            val topics = sendNotificationToTopicContainer.getTopics()
            call.respond(HttpStatusCode.OK, topics)
        }

        post("/topics") {
            val topicName = call.parameters.requireNotEmpty("topic")
            val result = sendNotificationToTopicContainer.isTopicAlreadyExists(topicName)
            call.respondWithResult(result, successMessage = "Topic is exist")
        }

        post("/subscribe") {
            val topicName = call.parameters.requireNotEmpty("topic")
            val token = call.parameters.requireNotEmpty("token")
            val result = sendNotificationToTopicContainer.subscribeTokenToTopic(topicName, token)
            call.respondWithResult(result, successMessage = "Subscribe Successfully")
        }

        post("/unsubscribe") {
            val topicName = call.parameters.requireNotEmpty("topic")
            val token = call.parameters.requireNotEmpty("token")
            val result = sendNotificationToTopicContainer.unsubscribeTokenFromTopic(topicName, token)
            call.respondWithResult(result, successMessage = "Unsubscribed Successfully")
        }

        post("/send/topic/{topicName}") {
            val topicName = call.parameters.requireNotEmpty("topicName")
            val receivedData = call.receiveParameters()
            val title = receivedData.requireNotEmpty("title")
            val body = receivedData.requireNotEmpty("body")
            val result = sendNotificationToTopicContainer.sendNotificationToTopic(topicName, title, body)
            call.respondWithResult(result, successMessage = "Notification sent successfully")
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