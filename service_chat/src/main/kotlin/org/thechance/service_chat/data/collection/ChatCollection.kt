package org.thechance.service_chat.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class ChatCollection(
    val ticketId : String,
    val userId: String,
    val supportId: String,
    val time : Long
){
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId()

    val messages : List<MessageCollection> = emptyList()
}