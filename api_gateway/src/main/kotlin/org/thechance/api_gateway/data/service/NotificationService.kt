package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.notification.NotificationDto
import org.thechance.api_gateway.data.model.notification.NotificationHistoryDto
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

    suspend fun deleteNotificationCollection(): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            method = { delete("notifications/deleteCollection") }
        )
    }

    suspend fun saveToken(userId: String, token: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { post("tokens/save-token/$userId?token=$token") }
        )
    }

    suspend fun deleteDeviceToken(userId: String, token: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { delete("device/token/$userId?deviceToken=$token") }
        )
    }

    suspend fun clearDevicesTokens(userId: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { delete("device/allTokens/$userId") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun sendNotificationToUser(notificationDto: NotificationDto, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            post("notifications/send/user") {
                body = Json.encodeToString(NotificationDto.serializer(), notificationDto)
            }
        }
    }

    suspend fun getNotificationHistoryForUser(
        userId: String,
        page: String,
        limit: String,
        languageCode: String
    ): PaginationResponse<NotificationHistoryDto> {
        return client.tryToExecute<PaginationResponse<NotificationHistoryDto>>(
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

    suspend fun getNotificationHistoryForUserInLast24Hours(
        userId: String,
        languageCode: String
    ): List<NotificationHistoryDto> {
        return client.tryToExecute<List<NotificationHistoryDto>>(
            APIs.NOTIFICATION_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("notifications/history-24hours/$userId") }
        )
    }
}