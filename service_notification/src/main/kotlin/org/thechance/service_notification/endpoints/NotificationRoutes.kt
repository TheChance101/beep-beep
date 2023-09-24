package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.domain.entity.InternalServerErrorException
import org.thechance.service_notification.domain.usecases.INotificationManagementUseCase
import org.thechance.service_notification.domain.usecases.IRegisterTokenUseCase
import org.thechance.service_notification.domain.usecases.ITopicManagementUseCase
import org.thechance.service_notification.endpoints.model.NotificationDto
import org.thechance.service_notification.endpoints.model.TokenRegistrationDto
import org.thechance.service_notification.endpoints.model.TopicSubscriptionDto
import org.thechance.service_notification.endpoints.utils.requireNotEmpty

fun Route.notificationRoutes() {

    val registerToken: IRegisterTokenUseCase by inject()
    val notificationManagement: INotificationManagementUseCase by inject()
    val topicManagement: ITopicManagementUseCase by inject()

    route("tokens") {
        post("/save-token") {
            val receivedData = call.receive<TokenRegistrationDto>()
            val result = registerToken.saveToken(receivedData.userId, receivedData.token)
            if (!result) throw InternalServerErrorException(TOKEN_NOT_REGISTERED)
            call.respond(HttpStatusCode.OK, "Token registered successfully")
        }
        get("/user/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val result = registerToken.getUserTokens(userId)
            call.respond(HttpStatusCode.OK, result)
        }
        post("/users") {
            val userIds  : List<String> = call.receive<List<String>>()
            val result = registerToken.getAllUsersTokens(userIds)
            call.respond(HttpStatusCode.OK, result)
        }
    }

    route("notifications") {
        post("send/user/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val receivedData = call.receive<NotificationDto>()
            val result = notificationManagement.sendNotificationToUser(
                userId,
                receivedData.title,
                receivedData.body
            )
            if (!result) throw InternalServerErrorException(NOTIFICATION_NOT_SENT)
            call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        post("send/topic/{topicName}") {
            val topicName = call.parameters.requireNotEmpty("topicName")
            val receivedData = call.receive<NotificationDto>()
            val result = notificationManagement.sendNotificationToTopic(
                topicName,
                receivedData.title,
                receivedData.body
            )
            if (!result) throw InternalServerErrorException(NOTIFICATION_NOT_SENT)
            call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        get("history") {
            val limit = call.parameters["limit"]?.toInt() ?: 10
            val page = call.parameters["page"]?.toInt() ?: 1
            val result = notificationManagement.getNotificationHistory(page, limit)
            call.respond(HttpStatusCode.OK, result.toDto())
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

        post("subscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            val result = topicManagement.subscribeToTopic(receivedData.topic, receivedData.token)
            if (!result) throw InternalServerErrorException(COULD_NOT_SUBSCRIBE_TO_TOPIC)
            call.respond(HttpStatusCode.OK, "Token subscribed successfully")
        }

        post("unsubscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            val result = topicManagement.unsubscribeFromTopic(receivedData.topic, receivedData.token)
            if (!result) throw InternalServerErrorException(COULD_NOT_UNSUBSCRIBE_FROM_TOPIC)
            call.respond(HttpStatusCode.OK, "Token unsubscribed successfully")
        }

    }
}