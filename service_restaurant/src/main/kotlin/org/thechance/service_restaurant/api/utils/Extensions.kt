package org.thechance.service_restaurant.api.utils

import io.ktor.http.*
import io.ktor.websocket.CloseReason
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.datetime.*

fun Parameters.extractString(key: String): String? {
    return this[key]?.trim()?.takeIf { it.isNotEmpty() }
}

fun Parameters.extractInt(key: String): Int? {
    return this[key]?.toIntOrNull()
}


fun currentTime(): LocalDateTime {
    val currentMoment: Instant = Clock.System.now()
    return currentMoment.toLocalDateTime(TimeZone.UTC)
}

suspend fun WebSocketSession?.closeSession(e: Exception = Exception()) {
    this?.close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, e!!.message ?: "Error occurred"))
}

