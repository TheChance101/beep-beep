package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import kotlinx.datetime.*
import java.time.LocalTime

fun Parameters.extractString(key: String): String? {
    return this[key]?.trim()?.takeIf { it.isNotEmpty() }
}

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}

fun currentTime(): Long {
    val currentMoment: Instant = Clock.System.now()
    return currentMoment.toEpochMilliseconds()
}

fun isRestaurantOpen(openTime: String, closeTime: String): Boolean {
    val now = LocalTime.now()
    return now.isAfter(LocalTime.parse(openTime)) && now.isBefore(LocalTime.parse(closeTime))
}