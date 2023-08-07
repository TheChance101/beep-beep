package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.domain.model.MissingRequestParameterException
import org.thechance.service_notification.domain.usecases.ISendNotificationsUseCase

fun Route.notificationRoutes() {

    val sendNotifications: ISendNotificationsUseCase by inject()

    post("notification/send") {
        val receivedData = call.receiveParameters()
        val title = receivedData["title"] ?: throw MissingRequestParameterException("4002")
        val body = receivedData["body"] ?: throw MissingRequestParameterException("4003")
        val userId = receivedData["userId"] ?: throw MissingRequestParameterException("4004")

        sendNotifications.sendNotificationToUser(userId, title, body)
        call.respond(HttpStatusCode.OK, "Notification sent successfully")
    }
}