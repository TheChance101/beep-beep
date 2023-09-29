package org.thechance.common.domain.entity

data class RestaurantInformation(
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val location: String,
    val openingTime : String,
    val closingTime : String,
)