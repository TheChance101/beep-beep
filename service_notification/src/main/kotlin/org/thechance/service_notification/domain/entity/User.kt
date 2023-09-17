package org.thechance.service_notification.domain.entity

data class User(
    val id: String,
    val userId: String,
    val deviceTokens: List<String>,
)

