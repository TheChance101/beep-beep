package org.thechance.service_chat.domain.utils

import kotlinx.datetime.*


fun currentDateTime(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
}

fun LocalDateTime.toMillis(): Long {
    return toInstant(TimeZone.UTC).toEpochMilliseconds()
}