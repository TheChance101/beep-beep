package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.MessageDto
import org.thechance.api_gateway.data.model.TicketDto
import org.thechance.api_gateway.data.utils.*
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

    suspend fun receiveTicket(supportId: String): Flow<TicketDto> {
        return client.tryToExecuteWebSocket<TicketDto>(
            api = APIs.CHAT_API,
            attributes = attributes,
            path = "/chat/tickets/$supportId",
        )
    }

    suspend fun sendAndReceiveMessage(message: MessageDto, ticketId: String) : Flow<MessageDto> {
        return client.tryToSendAndReceiveWebSocketData(
            data = message,
            api = APIs.CHAT_API,
            attributes = attributes,
            path = "/chat/$ticketId"
        )
    }

}
