package org.thechance.service_notification.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserNotificationsHistoryCollection(
    @Contextual
    @SerialName("user_id")
    val userId: ObjectId,
    @SerialName("notifications")
    val notifications: List<NotificationCollection>
)
