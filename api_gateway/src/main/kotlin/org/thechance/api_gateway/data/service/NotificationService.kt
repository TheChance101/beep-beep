package org.thechance.api_gateway.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.util.Attributes
import io.ktor.util.InternalAPI
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.notification.NotificationDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs

@Single
class NotificationService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {

    suspend fun getUserToken(id: String, languageCode: String): List<String> {
        return client.tryToExecute<List<String>>(
            APIs.NOTIFICATION_API, attributes = attributes, setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("tokens/user/$id")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun getAllUsersTokens(ids: List<String>, languageCode: String) {
        client.tryToExecute<List<String>>(
            APIs.NOTIFICATION_API, attributes = attributes, setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("tokens/users") {
                body = Json.encodeToString(ListSerializer(String.serializer()), ids)
            }
        }
    }

    suspend fun saveToken(userId: String, token: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { post("tokens/save-token/$userId?token=$token") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun sendNotificationToUser(
        userId: String,
        notificationDto: NotificationDto,
        languageCode: String
    ): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("notifications/send/user/$userId") {
                body = Json.encodeToString(NotificationDto.serializer(), notificationDto)
            }
        }
    }

    suspend fun getNotificationHistoryForUser(
        userId: String,
        page: String,
        limit: String,
        languageCode: String
    ): PaginationResponse<NotificationDto> {
        return client.tryToExecute<PaginationResponse<NotificationDto>>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            get("notifications/history/$userId") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }
}