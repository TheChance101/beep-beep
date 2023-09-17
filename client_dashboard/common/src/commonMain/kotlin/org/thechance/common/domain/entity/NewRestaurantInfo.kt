package org.thechance.common.domain.entity

data class NewRestaurantInfo(
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val location: String,
    val openingTime : String,
    val closingTime : String,
)