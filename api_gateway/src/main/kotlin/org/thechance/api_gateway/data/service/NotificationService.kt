package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.NotificationDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs

@Single
class NotificationService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler,
) {
    @OptIn(InternalAPI::class)
    suspend fun sendNotificationToUser(userId: String, notificationDto: NotificationDto, languageCode : String) : Boolean {
        return client.tryToExecute(
            api = APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ){
            post("notifications/send/user/$userId}") {
                body = Json.encodeToString(NotificationDto.serializer(),notificationDto)
            }
        }
    }

}