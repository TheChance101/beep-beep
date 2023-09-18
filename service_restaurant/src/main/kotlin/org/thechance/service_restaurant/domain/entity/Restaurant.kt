package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Restaurant(
    val id: String,
    val ownerId: String,
    val ownerUserName: String,
    val name: String,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val location: Location,
    val address: String,
    val currency: String,
) {
    fun isRestaurantOpen(): Boolean {
        val instantNow = Clock.System.now()
        val currentTime = Instant.parse(instantNow.toString()).toLocalDateTime(TimeZone.UTC).time
        val openLocalTime = LocalTime.parse(openingTime)
        val closeLocalTime = LocalTime.parse(closingTime)
        return currentTime in openLocalTime..closeLocalTime
    }
}
