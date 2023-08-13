package org.thechance.service_notification.domain.entity

data class Notification(
    val id : String,
    val title: String,
    val body: String,
    val date: Long,
    val userIds : List<String>,
)

data class NotificationRequest(
    val title: String,
    val body: String,
    val date: Long,
    val userIds : List<String>,
)