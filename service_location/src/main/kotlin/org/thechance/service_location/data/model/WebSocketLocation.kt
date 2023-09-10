package org.thechance.service_location.data.model

import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow


data class WebSocketLocation(
    val ownerSession: DefaultWebSocketServerSession,
) {
    val locations = MutableStateFlow(LocationDto())
}
