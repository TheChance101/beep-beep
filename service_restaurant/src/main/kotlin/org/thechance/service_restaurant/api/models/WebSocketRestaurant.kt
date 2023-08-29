package org.thechance.service_restaurant.api.models

import io.ktor.server.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow

data class WebSocketRestaurant(val ownerSession: DefaultWebSocketServerSession){
    val orders = MutableStateFlow(OrderDto())
}