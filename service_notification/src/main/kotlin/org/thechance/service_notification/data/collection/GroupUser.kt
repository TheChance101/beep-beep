package org.thechance.service_notification.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class GroupUser(
    @BsonId
    @SerialName("_id")
    @Contextual
    val userId: ObjectId,
)
