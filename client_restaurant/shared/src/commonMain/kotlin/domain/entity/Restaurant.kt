package domain.entity

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Restaurant(
    val id: String,
    val ownerId: String,
    val ownerUsername: String,
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val location: Location,
    val address: String
) {
    fun isRestaurantOpen(): Boolean {
        val instantNow = Clock.System.now()
        val currentTime = Instant.parse(instantNow.toString()).toLocalDateTime(TimeZone.UTC).time
        val openLocalTime = LocalTime.parse(openingTime)
        val closeLocalTime = LocalTime.parse(closingTime)
        return currentTime in openLocalTime..closeLocalTime
    }
}
