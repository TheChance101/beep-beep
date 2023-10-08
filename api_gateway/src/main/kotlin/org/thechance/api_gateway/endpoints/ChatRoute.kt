package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.TicketDto
import org.thechance.api_gateway.data.service.ChatService
import org.thechance.api_gateway.endpoints.utils.WebSocketServerHandler
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult


fun Route.chatRoute() {
    val chatService: ChatService by inject()
    val webSocketServerHandler: WebSocketServerHandler by inject()

    route("/chat") {

        post("/ticket") {
            val language = extractLocalizationHeader()
            val ticket = call.receive<TicketDto>()
            val result = chatService.createTicket(ticket,language)
            respondWithResult(HttpStatusCode.Created, result)
        }

        webSocket("/tickets/{supportId}") {
            val supportId = call.parameters["supportId"]?.trim().orEmpty()
            val tickets = chatService.receiveTicket(supportId)
            webSocketServerHandler.sessions[supportId] = this
            webSocketServerHandler.sessions[supportId]?.let {
                webSocketServerHandler.tryToCollectFormWebSocket(tickets, it)
            }
        }

        webSocket("/{ticketId}") {
            val ticketId = call.parameters["ticketId"]?.trim().orEmpty()
        }

    }
}