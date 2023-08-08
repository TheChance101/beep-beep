package org.thechance.service_notification.domain.model

data class User(
    val id: String,
    val deviceToken: List<String>,
    val topics: List<String>,
    val notifications: List<Notification>
)

