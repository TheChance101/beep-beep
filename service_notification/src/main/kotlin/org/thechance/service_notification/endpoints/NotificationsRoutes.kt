package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
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
        post("/register") {
            val receivedData = call.receive<TokenRegistrationDto>()
            registerToken(receivedData.userId, receivedData.token)
            call.respond(HttpStatusCode.OK, "Token registered successfully")
        }
    }

    route("notifications") {
        post("send/user/{userId}") {
            val userId = call.parameters.requireNotEmpty("userId")
            val receivedData = call.receive<NotificationDto>()
            notificationManagement.sendNotificationToUser(
                userId,
                receivedData.title,
                receivedData.body
            )
            call.respond(HttpStatusCode.OK, "Notification sent successfully")
        }

        post("send/topic/{topicName}") {
            val topicName = call.parameters.requireNotEmpty("topicName")
            val receivedData = call.receive<NotificationDto>()
            notificationManagement.sendNotificationToTopic(
                topicName,
                receivedData.title,
                receivedData.body
            )
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
            topicManagement.createTopic(topic)
            call.respond(HttpStatusCode.Created, "Topic created successfully")
        }

        get {
            val result = topicManagement.getTopics()
            call.respond(HttpStatusCode.OK, result)
        }

        post("subscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            topicManagement.subscribeToTopic(receivedData.topic, receivedData.token)
            call.respond(HttpStatusCode.OK, "Token subscribed successfully")
        }

        post("unsubscribe") {
            val receivedData = call.receive<TopicSubscriptionDto>()
            topicManagement.unsubscribeFromTopic(receivedData.topic, receivedData.token)
            call.respond(HttpStatusCode.OK, "Token unsubscribed successfully")
        }

    }
}