package org.thechance.service_taxi.api.dto.trip

import io.ktor.server.websocket.*

data class WebSocketTrip(
    val session: DefaultWebSocketServerSession,
    val isATaxiTrip: Boolean
)