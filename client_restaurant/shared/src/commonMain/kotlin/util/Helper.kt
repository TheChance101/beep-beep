package util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun isRestaurantOpen(openTime: String, closeTime: String): Boolean {
    val instantNow : Instant = Clock.System.now()
    val currentTime : LocalTime = Instant.parse(instantNow.toString()).toLocalDateTime(TimeZone.UTC).time
    val openLocalTime = LocalTime.parse(openTime)
    val closeLocalTime = LocalTime.parse(closeTime)
    return currentTime in openLocalTime..closeLocalTime
}