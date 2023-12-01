package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.data.mappers.toEntity
import org.thechance.service_notification.domain.entity.InternalServerErrorException
import org.thechance.service_notification.domain.usecases.INotificationManagementUseCase
import org.thechance.service_notification.domain.usecases.IRegisterTokenUseCase
import org.thechance.service_notification.domain.usecases.ITopicManagementUseCase
import org.thechance.service_notification.endpoints.model.BasePaginationResponseDto
import org.thechance.service_notification.endpoints.model.NotificationDto
import org.thechance.service_notification.endpoints.model.TopicSubscriptionDto
import org.thechance.service_notification.endpoints.utils.requireNotEmpty

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val notificationManagement: INotificationManagementUseCase by inject()
    val topicManagement: ITopicManagementUseCase by inject()

    route("tokens") {
        post("/save-token/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val token = call.parameters.requireNotEmpty("token")
            val result = registerToken.saveToken(userId, token)
            call.respond(HttpStatusCode.OK, result)
        }
        get("/user/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val result = registerToken.getUserTokens(userId)
            call.respond(HttpStatusCode.OK, result)
        }
        post("/users") {
            val userIds: List<String> = call.receive<List<String>>()
            val result = registerToken.getAllUsersTokens(userIds)
            call.respond(HttpStatusCode.OK, result)
        }
    }

    route("device") {

        delete("/token/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val deviceToken = call.parameters.requireNotEmpty("deviceToken")
            val result = notificationManagement.clearDeviceToken(deviceToken = deviceToken, userId = userId)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/allTokens/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val result = notificationManagement.clearAllDevicesTokensForUser(userId)
            call.respond(HttpStatusCode.OK, result)
        }
    }
    route("notifications") {

        delete("/deleteCollection") {
            val result = notificationManagement.deleteCollection()
            call.respond(HttpStatusCode.OK, result)
        }
        post("/send/user") {
            val receivedData = call.receive<NotificationDto>()
            val result = notificationManagement.sendNotificationToUser(receivedData.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        post("/send/topic") {
            val receivedData = call.receive<NotificationDto>()
            val result = notificationManagement.sendNotificationToTopic(receivedData.toEntity())
            if (!result) throw InternalServerErrorException(NOTIFICATION_NOT_SENT)
            call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        get("/history") {
            val limit = call.parameters["limit"]?.toInt() ?: 10
            val page = call.parameters["page"]?.toInt() ?: 1
            val result = notificationManagement.getNotificationHistory(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/history/{userId}") {
            val limit = call.parameters["limit"]?.toInt() ?: 10
            val page = call.parameters["page"]?.toInt() ?: 1
            val userId = call.parameters.requireNotEmpty("userId")
            val result = notificationManagement.getNotificationHistoryForUser(page, limit, userId).toDto()
            val total = notificationManagement.getTotalCountsOfNotificationHistoryForUser(userId)
            call.respond(HttpStatusCode.OK, BasePaginationResponseDto(items = result, page = page, total = total))
        }

        get("/history-24hours/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val result = notificationManagement.getNotificationHistoryInTheLast24Hours(userId).toDto()
            call.respond(HttpStatusCode.OK, result)
        }
    }

    route("topics") {
        post("/{topic}") {
            val topic = call.parameters.requireNotEmpty("topic")
            val result = topicManagement.createTopic(topic)
            if (!result) throw InternalServerErrorException(TOPIC_NOT_CREATED)
            call.respond(HttpStatusCode.Created, "Topic created successfully")
        }

        get {
            val result = topicManagement.getTopics()
            call.respond(HttpStatusCode.OK, result)
        }

        post("/subscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            val result = topicManagement.subscribeToTopic(receivedData.topic, receivedData.token)
            if (!result) throw InternalServerErrorException(COULD_NOT_SUBSCRIBE_TO_TOPIC)
            call.respond(HttpStatusCode.OK, "Token subscribed successfully")
        }

        post("/unsubscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            val result = topicManagement.unsubscribeFromTopic(receivedData.topic, receivedData.token)
            if (!result) throw InternalServerErrorException(COULD_NOT_UNSUBSCRIBE_FROM_TOPIC)
            call.respond(HttpStatusCode.OK, "Token unsubscribed successfully")
        }
    }
}