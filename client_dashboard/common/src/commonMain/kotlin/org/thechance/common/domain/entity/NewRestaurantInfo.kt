package org.thechance.common.domain.entity

data class NewRestaurantInfo(
    val name: String,
    val ownerUsername: String,
    val phoneNumber: String,
    val workingHours: Pair<Time, Time>,
    val location: String,
)