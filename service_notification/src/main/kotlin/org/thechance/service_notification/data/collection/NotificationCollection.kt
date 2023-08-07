package org.thechance.service_notification.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class NotificationCollection(
    @BsonId
    @SerialName("_id")
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("date")
    val date: Long
)
