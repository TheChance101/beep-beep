package org.thechance.service_notification.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserCollection(
    @BsonId
    @SerialName("_id")
    @Contextual
    val id: ObjectId,
    @SerialName("device_tokens")
    val deviceTokens: List<String>,
    @SerialName("topics")
    val topics: List<Int>
)
