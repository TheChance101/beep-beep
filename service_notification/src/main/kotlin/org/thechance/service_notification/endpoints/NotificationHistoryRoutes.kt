package org.thechance.service_notification.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_notification.data.mappers.toDto
import org.thechance.service_notification.domain.usecases.IGetNotificationHistoryUseCase
import org.thechance.service_notification.endpoints.utils.extractInt

fun Route.notificationHistoryRoutes() {

    val getNotificationHistoryUseCase: IGetNotificationHistoryUseCase by inject()

    get("notification/history") {
        val limit = call.parameters.extractInt("limit") ?: 10
        val page = call.parameters.extractInt("page") ?: 1

        val notificationsHistory = getNotificationHistoryUseCase.getNotificationsHistory(page, limit)
        call.respond(HttpStatusCode.OK, notificationsHistory.toDto())
    }


}