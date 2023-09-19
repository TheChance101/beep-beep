package org.thechance.service_chat.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class MessageCollection(
    @Contextual val id : ObjectId,
    val senderId : String,
    val content : String,
    val time : Long
)