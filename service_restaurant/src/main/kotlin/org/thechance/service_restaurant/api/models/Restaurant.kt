package org.thechance.service_restaurant.api.models

import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow

data class Restaurant(val ownerSession: WebSocketSession){
    val orders = MutableStateFlow(OrderDto())
}