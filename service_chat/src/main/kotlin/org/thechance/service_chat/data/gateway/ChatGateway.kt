package org.thechance.service_chat.data.gateway

import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Updates
import org.koin.core.annotation.Single
import org.litote.kmongo.eq
import org.thechance.service_chat.data.DataBaseContainer
import org.thechance.service_chat.data.collection.TicketCollection
import org.thechance.service_chat.data.collection.mappers.toCollection
import org.thechance.service_chat.data.collection.mappers.toEntity
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.gateway.IChatGateway

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
        )?.toEntity() ?: throw Throwable("not found")
    }

}