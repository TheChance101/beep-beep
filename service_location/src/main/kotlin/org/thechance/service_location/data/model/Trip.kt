package org.thechance.service_location.data.model

import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow

data class Trip(
    val session: DefaultWebSocketServerSession,
    val location: MutableStateFlow<LocationDto> = MutableStateFlow(LocationDto())
)
