package org.thechance.common.domain.entity

data class Restaurant(
    val id: String,
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val rating: Double,
    val priceLevel: Int,
    val workingHours: String,
)
