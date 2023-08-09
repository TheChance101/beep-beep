package org.thechance.service_notification.domain.model

data class Notification(
    val title: String,
    val body: String,
    val date: Long,
    val userId : String,
    val isRead : Boolean
)