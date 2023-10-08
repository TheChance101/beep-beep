package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TicketDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.data.utils.tryToExecuteFromWebSocket
import org.thechance.api_gateway.util.APIs


@Single
class ChatService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {
    @OptIn(InternalAPI::class)
    suspend fun createTicket(ticket: TicketDto, language: String): TicketDto {
        return client.tryToExecute<TicketDto>(
            api = APIs.CHAT_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, language)
            }
        ) {
            post("/chat/ticket") {
                body = Json.encodeToString(TicketDto.serializer(), ticket)
            }
        }
    }

    suspend fun receiveTicket(supportId: String): Flow<TicketDto?> {
        return client.tryToExecuteFromWebSocket<TicketDto?>(
            api = APIs.CHAT_API,
            attributes = attributes,
            path = "/chat/tickets/$supportId",
        )
    }

}
