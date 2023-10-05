package org.thechance.service_chat.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.ktor.ext.inject
import org.thechance.service_chat.domain.usecase.IManageChat
import org.thechance.service_chat.domain.usecase.IManageTicket
import org.thechance.service_chat.endpoints.models.Connection
import org.thechance.service_chat.endpoints.models.MessageDto
import org.thechance.service_chat.endpoints.models.SupportAgent
import org.thechance.service_chat.endpoints.models.TicketDto
import org.thechance.service_chat.endpoints.models.mappers.toDto
import org.thechance.service_chat.endpoints.models.mappers.toEntity
import java.util.*


fun Route.chatRoutes() {

    val manageTicket: IManageTicket by inject()
    val manageChat: IManageChat by inject()
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())


    route("/chat") {

        post("/ticket") {
            val chat = call.receive<TicketDto>()
            val result = manageTicket.createTicket(chat.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

        webSocket("/tickets/{supportId}") {
            val supportId = call.parameters["supportId"]?.trim().orEmpty()
            manageTicket.supports[supportId] = SupportAgent(session = this)
            manageTicket.notifySupportAgentOfNewTickets(supportId).collectLatest { ticket ->
                sendSerialized(ticket.toDto())
            }
        }

        webSocket("/{ticketId}") {
            val ticketId = call.parameters["ticketId"]?.trim().orEmpty()
            val thisConnection = Connection(this)
            if (connections.size > 1) {
                return@webSocket
            }
            connections += thisConnection
            try {
                val message = receiveDeserialized<MessageDto>()
                manageChat.saveMessage(ticketId, message.toEntity())
                connections.forEach {
                    it.session.sendSerialized(message)
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                connections -= thisConnection
            }
        }
    }
}