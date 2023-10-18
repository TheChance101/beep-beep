package org.thechance.service_restaurant.api.models

import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow

data class WebSocketOrder(
    val session: DefaultWebSocketServerSession,
    val order: MutableStateFlow<OrderDto> = MutableStateFlow(OrderDto())
)