package org.thechance.service_notification.domain.model

data class User (
    val id: String,
    val deviceTokens: List<String>,
    val topics: List<Int>,
    val notifications: List<String>
)