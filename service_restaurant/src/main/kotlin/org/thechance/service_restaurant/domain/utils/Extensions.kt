package org.thechance.service_restaurant.domain.utils

import kotlinx.datetime.*

fun Boolean.nullIfFalse(): Any? = if (!this) { null } else { Unit }

fun currentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
}

fun LocalDateTime.Companion.fromEpochMilliseconds(millis: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.UTC)
}

fun LocalDateTime.toMillis(): Long {
    return toInstant(TimeZone.UTC).toEpochMilliseconds()
}