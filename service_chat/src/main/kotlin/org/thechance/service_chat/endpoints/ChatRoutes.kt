package org.thechance.service_chat.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.usecase.IManageTicket
import org.thechance.service_chat.endpoints.models.TicketDto
import org.thechance.service_chat.endpoints.models.MessageDto


fun Route.chatRoutes() {

    val manageConversation: IManageTicket by inject()

    route("/chat") {

        post("/ticket") {
            val chat = call.receive<TicketDto>()
            val result = manageConversation.createConversation(chat.toEntity())
            call.respond(HttpStatusCode.Created, result.toDto())
        }

//        webSocket {
//
//        }
    }
}

fun Ticket.toDto(): TicketDto {
    return TicketDto(
        id = id,
        userId = userId,
        supportId = supportId,
        time = time,
        messages = messages.map { it.toDto() }
    )
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        id = id,
        senderId = senderId,
        content = content,
        time = time
    )
}


fun TicketDto.toEntity(): Ticket {
    return Ticket(
        id = id ?: "",
        userId = userId,
        supportId = supportId ?: "",
        time = time ?: 0,
        messages = messages?.map { it.toEntity() } ?: emptyList()
    )
}

fun MessageDto.toEntity(): Message {
    return Message(
        id = id ?: "",
        senderId = senderId ?: "",
        content = content ?: "",
        time = time ?: 0
    )
}