package org.thechance.service_chat.data.gateway

import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.thechance.service_chat.data.DataBaseContainer
import org.thechance.service_chat.data.collection.TicketCollection
import org.thechance.service_chat.data.collection.mappers.toCollection
import org.thechance.service_chat.data.collection.mappers.toEntity
import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.gateway.IChatGateway
import org.thechance.service_chat.domain.utils.MultiErrorException
import org.thechance.service_chat.domain.utils.TICKET_NOT_FOUND
import org.thechance.service_chat.domain.utils.TICKET_UPDATE_FAILED

@Single
class ChatGateway(private val container: DataBaseContainer) : IChatGateway {

    override suspend fun createTicket(ticket: Ticket): Ticket {
        val chatCollection = ticket.toCollection()
        container.ticketCollection.insertOne(chatCollection)
        return chatCollection.toEntity()
    }

    override suspend fun updateTicket(ticketId: String, supportId: String): Ticket {
        return container.ticketCollection.findOneAndUpdate(
            filter = TicketCollection::ticketId eq ticketId,
            update = Updates.set(TicketCollection::supportId.name, supportId),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER),
        )?.toEntity() ?: throw MultiErrorException(listOf(TICKET_NOT_FOUND))
    }


    override suspend fun saveMessage(ticketId: String, message: Message): Message {
        val ticket = container.ticketCollection.findOne(TicketCollection::ticketId eq ticketId)
        val newMessage = message.toCollection()
        ticket?.messages?.add(newMessage)
        ticket?.let {
            container.ticketCollection.updateOne(TicketCollection::ticketId eq ticketId, ticket)
        } ?: MultiErrorException(listOf(TICKET_NOT_FOUND))
        return newMessage.toEntity()
    }

    override suspend fun updateTicketState(ticketId: String, state: Boolean): Boolean {
        val updateOperation = setValue(TicketCollection::isOpen, state)
        return container.ticketCollection.findOneAndUpdate(
            filter = TicketCollection::ticketId eq ticketId,
            update = updateOperation
        )?.isOpen ?: throw MultiErrorException(listOf(TICKET_UPDATE_FAILED))
    }

}